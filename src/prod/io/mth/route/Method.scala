package io.mth.route

sealed trait Method

case object Get extends Method {

}
case object Put extends Method
case object Post extends Method
case object Head extends Method
case object Delete extends Method
case object Options extends Method
case object Trace extends Method
