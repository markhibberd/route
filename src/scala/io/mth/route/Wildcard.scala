package io.mth.route

import scala.util.control.Exception._
import scalaz.Monad

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

  def stringtoken: Wildcard[String] =
    wildcard(p => Some(p.fragment))

  def inttoken: Wildcard[Int] =
    wildcard(p => catching(classOf[NumberFormatException]) opt p.fragment.toInt)

  implicit val WildcardMonad: Monad[Wildcard] = new Monad[Wildcard] {
    def point[A](a: => A) = wildcard(_ => Some(a))
    def bind[A, B](a: Wildcard[A])(f: A => Wildcard[B]) = a flatMap f
    override def map[A, B](a: Wildcard[A])(f: A => B) = a map f
  }
}
