package io.mth.route

sealed trait Method

case object Get extends Method {
  override def toString = "GET"
}

case object Put extends Method {
  override def toString = "PUT"
}

case object Post extends Method {
  override def toString = "POST"
}

case object Head extends Method {
  override def toString = "HEAD"
}

case object Delete extends Method {
  override def toString = "DELETE"
}

case object Options extends Method {
  override def toString = "OPTIONS"
}

case object Trace extends Method {
  override def toString = "TRACE"
}
