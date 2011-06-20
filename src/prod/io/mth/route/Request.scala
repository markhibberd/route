package io.mth.route

trait Request {
  def method: Method
  def path: Path
  def contentType: ContentType
}

object Request extends Requests

trait Requests {
  def request(m: Method, p: Path, c: ContentType): Request = new Request {
    def method = m
    def path = p
    def contentType = c
  }

  def stringRequest(m: String, p: String, c: String) = 
    request(parseMethod(m), parsePath(p), contentType(c))
}
