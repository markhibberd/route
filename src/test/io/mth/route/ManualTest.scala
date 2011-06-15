package io.mth.route

import org.scalatest.FunSuite

class ManualTest extends FunSuite { 
  test("placeholder") {
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
