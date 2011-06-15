package io.mth.route

sealed trait ContentType {
  def fold[X](f: String => X): X
}


object ContentType {
  def contentType(mime: String): ContentType = new ContentType {
    def fold[X](f: String => X) = f(mime)
  }

  def html = contentType("text/html")
  def plain = contentType("text/plain")
}
