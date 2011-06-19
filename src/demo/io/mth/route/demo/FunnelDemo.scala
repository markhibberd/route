package io.mth.route.demo

import io.mth.route._
import Path._
import Request._

object FunnelDemo {
  def main(args: Array[String]) {
    val route = ("foo" </> "bar").route(
      Get("foo/bar.get")
    )

    val r1 = request("GET", "foo/bar", "text/html")
    val r2 = request("PUT", "foo/bar", "text/html")
    val r3 = request("GET", "foo/baz", "text/html")


    val x1 = route.v(r1)
    val x2 = route.v(r2)
    val x3 = route.v(r3)


    def print[A](r: Response[A]): Unit = r.fold(
      "found [" + _ + "]",
      "notfound",
      "fail [" + _ + "]"
    )

    print(x1)
    print(x2)
    print(x3)
  }
/*
  def route =
    ("foo" </> "bar").route(
      Get.route({
        "get[" + _ + "]"
        html.route()
      
      })
      Get => "get[" + _ + "]
    )

    ("foo" </> "bar").resource(Get.route(a) | Post.route(b))

    ("foo" </> "bar").resource(Get.route(a) | Post.route(b))
    *
    ("foo" </> "bar").resource(
      contentType r
      (Get.route(a) | Post.route(b))
    )
    *
    ("foo" </> numeric)

    ("foo" </> string)

    
  
      
    )

    
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
*/
}
