package io.mth.route

sealed trait RequestSignature {
  def fold[X](f: (Method, Path) => X): X

  def path = fold((_, p) => p)

  def method = fold((m, _) => m)

  def </>(part: Path): RequestSignature = RequestSignature.request(method, path </> part)

  override def toString = method.toString + " " + path.toString

  override def equals(obj: Any) =
    obj.isInstanceOf[RequestSignature] &&
    obj.asInstanceOf[RequestSignature].fold((m1, p1) => fold((m2, p2) => m1 == m2 && p1 == p2))
}


object RequestSignature {
  def request(rmethod: Method, rpath: Path): RequestSignature = new RequestSignature {
    def fold[X](f: (Method, Path) => X) = { f(rmethod, rpath) }
  }
}
