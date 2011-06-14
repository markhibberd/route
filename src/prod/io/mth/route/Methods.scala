package io.mth.route

sealed trait Methods {
  def fold[X](m: List[Method] => X): X

  def |(method: Method): Methods = new Methods {
    def fold[X](m: List[Method] => X) = m(Methods.this.fold(x => x ::: List(method)))
  }

  def matches(method: Method) = fold(_.exists(_ == method))

  def find(method: Method) = fold(_.find(_ == method))
}

object Methods {
  implicit def Method2Methods(m: Method) = methods(m)

  def methods(method: Method): Methods = new Methods {
    def fold[X](m: List[Method] => X) = m(method :: Nil)
  }
}
