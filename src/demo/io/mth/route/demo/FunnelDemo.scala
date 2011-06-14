package io.mth.route.demo

import io.mth.route._

object FunnelDemo {
  def route =
    id(Get, "foo" </> "bar", "get[" + _ + "]") :>:
    id(Post, "foo" </> "bar", "post[" + _ + "]") :>:
    is(Get, "foo" </> "bar" </> "baz", "exact match") :>:
    rule(rs => Some("special case")) :>:
    base("base case")
  
   def main(args: Array[String]) = {
    val line = request(Post, "foo" </> "bar" </> "baz")
    val result = route(line)
    println(result)
  }
}
