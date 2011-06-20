package io.mth.route

import scalaz._, Scalaz._

sealed trait Part {
  val fragment: String

  def </>(p: Path): Path = 
    toPath </> p

  def <%>[A](w: Wildcard[A]): WildcardPath[A] = 
    toPath <%> w

  def toPath = Path.path(this :: Nil)

  override def toString = fragment 

  override def hashCode = fragment.hashCode
  
  override def equals(o: Any) = 
    o.isInstanceOf[Part] &&
    o.asInstanceOf[Part].fragment == fragment
}

object Part extends Parts

trait Parts {
  def part(f: String): Part = new Part {
    val fragment = f
  }

  implicit def StringToPart(s: String) = part(s)  
}
