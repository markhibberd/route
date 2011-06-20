package io.mth.route

import scalaz._, Scalaz._

sealed trait Wildcard[A] {
  val parse: Part => Option[A] 

  def apply(p: Part) = parse(p)

  def map[B](f: A => B) =
    wildcard(p => parse(p) map f)

  def flatMap[B](f: A => Wildcard[B]) = 
    wildcard(p => parse(p) flatMap (a => f(a)(p)))
}

object Wildcard extends Wildcards 

trait Wildcards {
  def wildcard[A](f: Part => Option[A]): Wildcard[A] = new Wildcard[A] {
    val parse = f
  }

  def stringx: Wildcard[String] = 
    wildcard(p => Some(p.fragment))

  implicit val WildcardFunctor: Functor[Wildcard] = new Functor[Wildcard] {
    def fmap[A, B](a: Wildcard[A], f: A => B) = a map f
  }

  implicit val WildcardPure: Pure[Wildcard] = new Pure[Wildcard] {
    def pure[A](a: => A) = wildcard(_ => Some(a))
  }

  implicit val WildcardBind: Bind[Wildcard] = new Bind[Wildcard] {
    def bind[A, B](a: Wildcard[A], f: A => Wildcard[B]) = a flatMap f
  }
}
