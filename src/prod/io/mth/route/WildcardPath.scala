package io.mth.route

import scalaz._, Scalaz._

sealed trait Part[A] {
  def fold[X](
    parse: (String => A) => X,
    fixed: String => X
  ): X
}

sealed trait WildcardPath[A] {
  def matcher: Path => Response[A]
}

object WildcardPath {

}

