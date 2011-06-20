package io.mth.route

import org.specs2.mutable._
import org.scalacheck._
import org.specs2.ScalaCheck
import Data._
import RouteMatchers._

class PathSpec extends Specification with ScalaCheck { 
  "request path should always match" in check { (r: Request, atom: String) => 
    r.path(atom)(r) must matchResponse(atom)
  }
}
