package io.mth.route

import scalaz._, Scalaz._

sealed trait ContentType {
  val value: String

  def route[A](r: Route[A]): Route[A] = 
    Route.route(req => 
      if (value == req.contentType)
        r(req)
      else
        notfound
    )

  def apply[A](a: A) = constant(a)

  def constant[A](a: A): Route[A] = 
    route(a.pure[Route])
}

object ContentType extends ContentTypes

trait ContentTypes {
  def contentType(mime: String): ContentType = new ContentType {
    val value = mime
  }

  def html = contentType("text/html")
  def plain = contentType("text/plain")
}
