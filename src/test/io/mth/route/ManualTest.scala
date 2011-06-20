package io.mth.route

import org.scalatest.FunSuite


class ManualTest extends FunSuite { 
  test("printing things") {
    val route = ("foo" </> "bar").route(
      Post("foo/bar.post") |
      Get.route(
        ContentType.html("foo/bar.get.html") |
        ContentType.plain("foo/bar.get.plain")
      )
    ) | ("baz" <%> stringx).route(s =>
      Put("baz/" + s + ".put")
    ) | ("bat" <%> stringx <%%> (stringx, (s1: String, s2: String) => s1 + ":" + s2)).route(s =>
      Get("bat/" + s + ".put")
    )

    val requests = List(
      stringRequest("POST", "foo/bar", "text/html"),
      stringRequest("GET", "foo/bar", "text/html"),
      stringRequest("GET", "foo/bar", "text/plain"),
      stringRequest("GET", "foo/bar", "application/json"),
      stringRequest("PUT", "foo/bar", "text/html"),
      stringRequest("GET", "foo/baz", "text/html"),
      stringRequest("PUT", "baz/a", "text/html"),
      stringRequest("PUT", "baz/b", "text/html"),
      stringRequest("GET", "baz/b", "text/html"),
      stringRequest("GET", "bat/1/2", "text/html"),
      stringRequest("GET", "bat/3/4", "text/html"),
      stringRequest("GET", "bat/3/4/5", "text/html"),
      stringRequest("GET", "bat/3", "text/html")
    )

    def print[A](r: Response[A]): Unit = println(r.fold(
      "found [" + _ + "]",
      "notfound",
      "fail [" + _ + "]"
    ))

    requests.foreach(r => print(route(r)))
  }
}
