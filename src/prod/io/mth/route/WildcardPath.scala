package io.mth.route

import scalaz._, Scalaz._

sealed trait WildcardPath[A] {
  val matcher: Path => Response[(Path, A)]

  def map[B](f: A => B) = 
    wildcardpath(p => matcher(p) map {
      case (rest, a) => (rest, f(a))
    })

  def flatMap[B](f: A => WildcardPath[B]) = 
    wildcardpath(p => matcher(p) flatMap {
      case (rest, a) => f(a).matcher(rest)
    })

  def </>(path: Path): WildcardPath[A] = 
    wildcardpath(p => matcher(p) flatMap {
      case (rest, a) => 
        if (rest.parts == path.parts) // FIX looks like a bug, should this be starts with?
          found((Path.base, a))
        else
          notfound
    })
  
  def <%>[B](w: Wildcard[B]): WildcardPath[(A, B)] = 
    this <%%> (w, (a: A, b: B) => (a, b))

  def <%%>[B, C](w: Wildcard[B], f: (A, B) => C): WildcardPath[C] = 
    wildcardpath(p => matcher(p) flatMap {
      case (rest, a) => 
        if (rest.length >= 1)
          w.parse(rest.head) match {
            case Some(b) => found((rest.tail, f(a, b)))
            case None => notfound
          }
        else
          notfound
    })

  def rest: WildcardPath[(A, String)] =
    wildcardpath(p => matcher(p) flatMap {
      case (rest, a) =>
          found((Path.base, (a, rest.asString)))
    })

  def apply[B](r: A => B): Route[B] =
    route(a => r(a).pure[Route])

  def route[B](r: A => Route[B]): Route[B] = 
    Route.route(req =>
      matcher(req.path) flatMap {
        case (rest, a) => {
          if (rest.length == 0)
            r(a)(req)
          else
            notfound
        }
      })
    
  def constant[B](b: B): Route[B] = 
    apply(_ => b)
}

object WildcardPath extends WildcardPaths

trait WildcardPaths {
  def wildcardpath[A](f: Path => Response[(Path, A)]): WildcardPath[A] = new WildcardPath[A] {
    val matcher = f
  }

  def wildpart[A](path: Path, w: Wildcard[A]) = wildcardpath[A](p => {
    p.startsWith(path, 
      rest => {
        if (rest.length >= 1)
          w.parse(rest.head) match {
            case Some(a) => found((rest.tail, a))
            case None => notfound
          }
        else
          notfound
      },
      notfound
    )
  })

  implicit val WildcardPathFunctor: Functor[WildcardPath] = new Functor[WildcardPath] {
    def fmap[A, B](a: WildcardPath[A], f: A => B) = a map f
  }

  implicit val WildcardPathPure: Pure[WildcardPath] = new Pure[WildcardPath] {
    def pure[A](a: => A) = wildcardpath(_ => found((Path.base, a)))
  }

  implicit val WildcardPathBind: Bind[WildcardPath] = new Bind[WildcardPath] {
    def bind[A, B](a: WildcardPath[A], f: A => WildcardPath[B]) = a flatMap f
  }
}

