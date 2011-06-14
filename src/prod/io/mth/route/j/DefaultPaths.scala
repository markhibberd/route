package io.mth.route.j

import java.lang.String
import io.mth.route.Path
;

class DefaultPaths extends Paths {
  def parse(s: String) = Path.parse(s)
}
