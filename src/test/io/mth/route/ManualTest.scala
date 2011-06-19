package io.mth.route

import org.scalatest.FunSuite
import Path._
import Request._


class ManualTest extends FunSuite { 
  test("printing things") {
    val route = ("foo" </> "bar").route(
      Post("foo/bar.post") |
      Get.route(
        ContentType.html("foo/bar.get.html") |
        ContentType.plain("foo/bar.get.plain")
      )
    )

    val requests = List(
      request("POST", "foo/bar", "text/html"),
      request("GET", "foo/bar", "text/html"),
      request("GET", "foo/bar", "text/plain"),
      request("GET", "foo/bar", "application/json"),
      request("PUT", "foo/bar", "text/html"),
      request("GET", "foo/baz", "text/html")
    )

    def print[A](r: Response[A]): Unit = println(r.fold(
      "found [" + _ + "]",
      "notfound",
      "fail [" + _ + "]"
    ))

    requests.foreach(r => print(route(r)))
  }
}
