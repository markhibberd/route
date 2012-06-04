package io.mth.route

import scalaz._, Scalaz._

sealed trait Method {
  def route[A](r: Route[A]): Route[A] =
    Route.route(req =>
      if (this == req.method)
        r(req)
      else
        notfound
    )

  def apply[A](a: A) = constant(a)

  def constant[A](a: A): Route[A] =
    route(a.point[Route])
}

object Method extends Methods

trait Methods {
  def parseMethod(m: String) = m match {
    case "GET" => Get
    case "PUT" => Put
    case "POST" => Post
    case "HEAD" => Head
    case "DELETE" => Delete
    case "OPTIONS" => Options
    case "TRACE" => Trace
  }
}

case object Get extends Method
case object Put extends Method
case object Post extends Method
case object Head extends Method
case object Delete extends Method
case object Options extends Method
case object Trace extends Method
