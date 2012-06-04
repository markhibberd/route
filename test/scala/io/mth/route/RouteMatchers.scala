package io.mth.route


import org.specs2.matcher._, Matchers._

object RouteMatchers {

  val m: Matcher[String]  = ((_: String).startsWith("hello"), "doesn't start with hello")
  def matchResponse[A](a: A): Matcher[Response[A]] =  
    ((_: Response[A]).fold(aa => aa == a,  false, _ => false),
     (_: Response[A]) => "response matched [" + a + "]",
     (_: Response[A]).fold(
        aa => "response did not match [" + a + "], was [" + aa + "]", 
        "response not found", 
        f => "response failed [" + f.message + "]"
    ))
}
