package io.mth.route

trait Request {
  def method: Method
  def path: String
  def contentType: String
}

object Request {
  def request(m: Method, p: String, c: String): Request = new Request {
    def method: Method = m
    def path: String = p
    def contentType: String = c
  }
}
