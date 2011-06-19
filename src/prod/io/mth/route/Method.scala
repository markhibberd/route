package io.mth.route

import scalaz._, Scalaz._

sealed trait Method {
  import Path._
  import Response._

  def route[A](r: Route[A]): Route[A] = 
    Route.route(req => 
      if (matches(req.method))
        r(req)
      else
        notfound
    )

  def apply[A](a: A) = constant(a)

  def constant[A](a: A): Route[A] = 
    route(a.pure[Route])

  def matches(m: String) = this match {
    case Get => m == "GET"
    case Put => m == "PUT"
    case Post => m == "POST"
    case Head => m == "HEAD"
    case Delete => m == "DELETE"
    case Options => m == "OPTIONS"
    case Trace => m == "TRACE"
  }
}

case object Get extends Method
case object Put extends Method
case object Post extends Method
case object Head extends Method
case object Delete extends Method
case object Options extends Method
case object Trace extends Method
