package io.mth.route

import org.specs2._
import runner.FilesRunner 
import main._

class AllSpecs extends Specification with FilesRunner { def is =
  specifications(basePath = "src/test").map(spec => spec.is).reduceLeft((a, b) => a ^ b)
}

