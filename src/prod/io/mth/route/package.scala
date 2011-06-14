package io.mth

import route.{Method, Funnel, RequestSignature, Methods, Path}

package object route {
  implicit def String2Path(s: String) = Path.part(s)
  
  def request(method: Method, path: Path) = RequestSignature.request(method, path)
  
  def base[A](a: => A) = Funnel.base(a)

  def rule[A](f: RequestSignature => Option[A]) = Funnel.rule(f)

  def is[A](m: Methods, p: Path, a: => A) = Funnel.is(m, p, a)

  def id[A](m: Methods, p: Path, a: String => A) = Funnel.id(m, p, a)
}
