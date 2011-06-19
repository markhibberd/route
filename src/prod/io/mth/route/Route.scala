package io.mth.route

import scalaz._, Scalaz._

sealed trait Route[A] {
  import Route._
  import Response._

  val dispatch: Request => Response[A]

  def apply(request: Request) = 
    dispatch(request)

  def map[B](f: A => B): Route[B] = 
    route(r => dispatch(r) map (f))

  def flatMap[B](f: A => Route[B]): Route[B] = 
    route(r => dispatch(r) flatMap (a => f(a)(r)))

  def chain(a: Route[A]) = 
    route[A](r => dispatch(r).fold(
      a => found(a),
      a(r),
      f => failure(f)
    ))

  def |(r: Route[A]): Route[A] = 
    chain(r)
}

object Route {
  import Response._

  def route[A](f: Request => Response[A]): Route[A] = new Route[A] {
    val dispatch = f
  }

  def constant[A](a: A): Route[A] = route(_ => found(a))

  implicit val RouteFunctor: Functor[Route] = new Functor[Route] {
    def fmap[A, B](a: Route[A], f: A => B) = a map f
  }

  implicit val RoutePure: Pure[Route] = new Pure[Route] {
    def pure[A](a: => A) = constant(a)
  }

  implicit val RouteBind: Bind[Route] = new Bind[Route] {
    def bind[A, B](a: Route[A], f: A => Route[B]) = a flatMap f
  }
}
