package io.mth.route

import org.specs2._
import org.scalacheck._

class ManualSpec extends Specification with ScalaCheck { def is =

  "startsWith" ! check { (a: String, b: String) => (a+b).startsWith(a) }                                                ^
  "endsWith"   ! check { (a: String, b: String) => (a+b).endsWith(b) }                                                  ^
  "concat"     ! check { (a: String, b: String) => (a+b).length > a.length && (a+b).length > b.length }                 ^
  "substring"  ! check { (a: String, b: String) => (a+b).substring(a.length) == b }                                     ^
  "substring"  ! check { (a: String, b: String, c: String) => (a+b+c).substring(a.length, a.length+b.length) == b }     ^
                                                                                                                        end
}

