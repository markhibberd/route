package io.mth.route

import org.specs2.mutable._
import io.mth.route.demo.Demo


class DemoSpec extends Specification { 
  "run" in { 
    Demo.run must_== ()
  }
}
