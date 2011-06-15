package io.mth.route.j

import io.mth.route.{RequestSignature, Path}

class DefaultRequests extends Requests {
  import Conversions._

  def request(method: Method, path: Path) = RequestSignature.request(method, path)
  def request(method: String, path: Path) = RequestSignature.request(Method.valueOf(method.toUpperCase), path)
}
