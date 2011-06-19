package io.mth.route

import scalaz._, Scalaz._

sealed trait Path {
  import Path._
  import Response._

  def fold[X](f: List[String] => X): X

  def </>(req: Path): Path = new Path {
    val combined = Path.this.fold(p1 => req.fold(p2 => p1 ++ p2))
    def fold[X](f: List[String] => X) = {
      f(combined)
    }
  }

  def apply[A](a: A) = constant(a)

  def route[A](r: Route[A]): Route[A] = 
    Route.route(req => 
      if (matches(req.path))
        r.v(req)
      else
        notfound
    )
    
  def matches(path: String) = 
    fold(_.mkString("/") == path)

  def constant[A](a: A): Route[A] = 
    route(a.pure[Route])
}

object Path {
  def base: Path = new Path {
    def fold[X](f: List[String] => X) = f(Nil)
  }

  def part(s: String): Path = new Path {
    def fold[X](f: List[String] => X) = f(s :: Nil)
  }

  def parse(s: String): Path = s.split('/').foldLeft(base)((acc, v) => acc </> part(v))

  implicit def StringToPath(s: String) = part(s)
}
