package io.mth.route

import org.specs2.mutable._
import org.scalacheck._
import org.specs2.ScalaCheck
import Data._
import RouteMatchers._

class ContentTypeSpec extends Specification with ScalaCheck { 
  "request content type should always match" in prop { (r: Request, atom: String) =>
    r.contentType(atom)(r) must matchResponse(atom)
  }
}
