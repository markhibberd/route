package io.mth.route

import org.scalatest.FunSuite
import Path._
import Request._


class ManualTest extends FunSuite { 
  test("printing things") {
    val route = ("foo" </> "bar").route(
      Get("foo/bar.get")
    )

    val r1 = request("GET", "foo/bar", "text/html")
    val r2 = request("PUT", "foo/bar", "text/html")
    val r3 = request("GET", "foo/baz", "text/html")


    val x1 = route.v(r1)
    val x2 = route.v(r1)
    val x3 = route.v(r1)


    def print[A](r: Response[A]): Unit = println(r.fold(
      "found [" + _ + "]",
      "notfound",
      "fail [" + _ + "]"
    ))

    print(x1)
    print(x2)
    print(x3)
/*
    import Path._

    (base </> "fred" </> "bar").route(
      Get("fred.bar.get") || Put("fred.bar.put")
    ) ||
    (base </> "fred" </> "bob").route(
      (Get("bob") || Put("bob")).flatMap(v => 
        html(renderhtml(v)) || xml(renderbobxml(v))
      )
    ) ||
    (base </> "fred" </> string).parameterized(id =>
      Get("fred." + id)
    )
  */  
  }
}
