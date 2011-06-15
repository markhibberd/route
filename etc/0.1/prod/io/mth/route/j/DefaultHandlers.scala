package io.mth.route.j

import java.lang.String
import io.mth.route.{Funnel, RequestSignature, Path, Methods}

class DefaultHandlers extends Handlers {
  import Conversions._
  import Methods._

  def id[A](method: Method, path: String, a: F[String, A]) = handler(Funnel.id(methods(method), Path.parse(path), s => a(s)))

  def is[A](method: Method, path: String, a: BaseCase[A]) = handler(Funnel.is(methods(method), Path.parse(path), a.value))

  def is[A](method: Method, path: String, a: A) = handler(Funnel.is(methods(method), Path.parse(path), a))

  def handler[A](f: RequestSignature => Option[A]): Handler[A] = new Handler[A] {
    def apply(request: RequestSignature) = f(request)
  }
}
