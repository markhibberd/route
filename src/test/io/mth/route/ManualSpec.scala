package io.mth.route

import org.specs2.mutable._
import org.scalacheck._
import org.specs2.ScalaCheck

class ManualSpec extends Specification with ScalaCheck { 
  "ex" in check { (a: Int, b: Int) => a + b == b + a }
}

