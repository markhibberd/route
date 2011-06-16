package io.mth.route

import scalaz._, Scalaz._

sealed trait Route[A] {
  import Route._
  import Routes._

  val v: Request => Response[A]

  def map[B](f: A => B): Route[B] = 
    route(r => v(r) map (f))

  def flatMap[B](f: A => Route[B]): Route[B] = 
    route(r => v(r) flatMap (a => f(a).v(r)))

  def |(r: Route[A]): Routes[A] = 
    compound(this :: r :: Nil)
}

object Route {
  import Response._

  def route[A](f: Request => Response[A]): Route[A] = new Route[A] {
    val v = f
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
