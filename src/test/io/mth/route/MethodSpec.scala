package io.mth.route

import org.specs2.mutable._
import org.scalacheck._
import org.specs2.ScalaCheck
import Data._

class MethodSpec extends Specification with ScalaCheck { 
  "method constant path" in check { (m: Method, p: Path, c: ContentType, s: String) => 
    m(s)(request(m, p, c)).fold(
      r => r === s,
      false,
      _ => false)
  }
}
