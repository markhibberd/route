package io.mth.route

import org.specs2.mutable._
import org.scalacheck._
import org.specs2.ScalaCheck
import Data._
import RouteMatchers._

class WildcardPathSpec extends Specification with ScalaCheck { 
  "init of request path should always match string wild card" in prop { (r: Request, atom: String) =>
    (r.path.init <%> stringtoken).constant(atom)(r) must matchResponse(atom)
  }
}
