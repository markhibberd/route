package io.mth.route

import scalaz._, Scalaz._

trait Response[A] {
  import Response._

  def fold[X](
    found: A => X,
    notfound: => X,
    failure: Failure => X
  ): X

  def map[B](f: A => B): Response[B] = 
    flatMap(a => found(f(a)))
  
  def flatMap[B](f: A => Response[B]): Response[B] = fold(
    a => f(a),
    notfound,
    f => failure(f)
  )
}

object Response {
  def found[A](a: A): Response[A] = new Response[A] {
    def fold[X](
      found: A => X,
      notfound: => X,
      failure: Failure => X
    ) = found(a)
  }

  def notfound[A]: Response[A] = new Response[A] {
    def fold[X](
      found: A => X,
      notfound: => X,
      failure: Failure => X
    ) = notfound
  }

  def failure[A](e: Failure): Response[A] = new Response[A] {
    def fold[X](
      found: A => X,
      notfound: => X,
      failure: Failure => X
    ) = failure(e)
  }

  implicit val ResponseFunctor: Functor[Response] = new Functor[Response] {
    def fmap[A, B](a: Response[A], f: A => B) = a map f
  }

  implicit val ResponsePure: Pure[Response] = new Pure[Response] {
    def pure[A](a: => A) = found(a)
  }

  implicit val ResponseBind: Bind[Response] = new Bind[Response] {
    def bind[A, B](a: Response[A], f: A => Response[B]) = a flatMap f
  }
}
