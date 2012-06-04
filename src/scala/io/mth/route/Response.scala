package io.mth.route

import scalaz._, Scalaz._

trait Response[A] {
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

  def toOption = fold(
    a => Some(a),
    None, 
    _ => None
  )

  def or(a: => A) = 
    toOption.getOrElse(a)

  override def toString = fold(
    a => "value[" + a + "]",
    "notfound",
    f => "failure[" + f.message + "]"
  )

  override def equals(o: Any) = 
    o.isInstanceOf[Response[_]] &&
    o.asInstanceOf[Response[_]].fold(
      a => fold(_ == a, false, _ => false),
      fold(_ => false, true, _ => false),
      f => fold(_ => false, false, _ == f)
    )
        
  override def hashCode = fold(
    _.hashCode,
    0,
    _.hashCode
  )
}

object Response extends Responses

trait Responses {
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
