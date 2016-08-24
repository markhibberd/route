package io.mth.route

import org.specs2.mutable._
import org.scalacheck._
import org.specs2.ScalaCheck
import Data._
import RouteMatchers._

class MethodSpec extends Specification with ScalaCheck { 
  "request method should always match" in prop { (r: Request, atom: String) =>
    r.method(atom)(r) must matchResponse(atom)
  }
}
