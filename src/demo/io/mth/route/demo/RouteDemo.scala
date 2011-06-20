package io.mth.route.demo

import io.mth.route._
import scalaz._, Scalaz._

// FIX Clean this up into a reasonable demo.
object RouteDemo {
  def main(args: Array[String]) = run

  def route = ("foo" </> "bar").route(
    Post("foo/bar.post") |
    Get.route(
      text.html("foo/bar.get.html") |
      text.plain("foo/bar.get.plain")
    )
  ) | ("baz" <%> stringtoken).route(s =>
    Put("baz/" + s + ".put")
  ) | ("bat" <%> inttoken <%%> (inttoken, (i1: Int, i2: Int) => i1 + i2)).route(s =>
    Get("bat/" + s + ".put")
  )

  def requests = List(
    (Post, "foo" </> "bar", text.html),
    (Get,  "foo" </> "bar", text.html),
    (Get,  "foo" </> "bar", text.plain),
    (Get,  "foo" </> "bar", application.json),
    (Put,  "foo" </> "bar", text.html),
    (Get,  "foo" </> "baz", text.html),
    (Put,  "baz" </> "a", text.html),
    (Put,  "baz" </> "b", text.html),
    (Get,  "baz" </> "b", text.html),
    (Get,  "bat" </> "1" </> "2", text.html),
    (Get,  "bat" </> "3" </> "4", text.html),
    (Get,  "bat" </> "3" </> "4" </> "5", text.html),
    (Get,  "bat" </> "3", text.html)
  ) map {
    case (m, p, c) => request(m, p, c)
  }

  def expected = List(
    found("foo/bar.post"),
    found("foo/bar.get.html"),
    found("foo/bar.get.plain"),
    notfound,
    notfound,
    notfound,
    found("baz/a.put"),
    found("baz/b.put"),
    notfound,
    found("bat/3.put"),
    found("bat/7.put"),
    notfound,
    notfound
  )

  def run {
    assert(requests.map(r => route(r)) == expected)
  }
}
