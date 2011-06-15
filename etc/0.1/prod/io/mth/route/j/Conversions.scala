package io.mth.route.j

import io.mth.route.{Funnel, RequestSignature, Path, Get, Put, Post, Head, Trace, Options, Delete}
import io.mth.route.{Method => SMethod}

object Conversions {
  implicit def ToMethod(method: Method): SMethod = method match {
    case Method.GET => Get
    case Method.PUT => Put
    case Method.POST => Post
    case Method.HEAD => Head
    case Method.DELETE => Delete
    case Method.TRACE => Trace
    case Method.OPTIONS => Options
  }
}
