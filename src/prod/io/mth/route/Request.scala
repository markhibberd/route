package io.mth.route

trait Request {
  def method: String
  def path: String
  def contentType: String
}

object Request {
  def request(m: String, p: String, c: String): Request = new Request {
    def method: String = m
    def path: String = p
    def contentType: String = c
  }
}
