package io.mth.route.j

import java.util.List
import scala.collection.JavaConversions._
import io.mth.route.{RequestSignature, Funnel}

class DefaultRoutes extends Routes {
  def build[A](handlers: List[Handler[A]], basecase: BaseCase[A]): Route[A] = new Route[A] {
    def route(request: RequestSignature) =
      handlers.foldRight(Funnel.base(basecase.value))((v, acc) => v :>: acc)(request)
  }

  def build[A](handlers: List[Handler[A]], basecase: A): Route[A] = build(handlers, new DefaultBaseCase(basecase))
}
