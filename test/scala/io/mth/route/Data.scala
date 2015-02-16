package io.mth.route

import org.scalacheck._
import Arbitrary.arbitrary
import Gen.{frequency, choose, listOfN, oneOf}

object Data {
  implicit def ArbitraryMethod: Arbitrary[Method] = Arbitrary(
    oneOf(List(Get, Put, Post, Head, Delete, Options, Trace))
  )

  implicit def ArbitraryPart: Arbitrary[Part] = 
    Arbitrary(arbitrary[String] map (part))

  implicit def ArbitraryPath: Arbitrary[Path] = 
    Arbitrary(for {
      n <- choose(1, 4)
      ps <- listOfN(n, arbitrary[Part])
    } yield path(ps))

  implicit def ArbitraryContentType: Arbitrary[ContentType] = Arbitrary(
    oneOf(List(text.html, text.plain, text.json, text.xml, application.json, application.xml))
  )

  implicit def ArbitraryRequest: Arbitrary[Request] = 
    Arbitrary(for {
      m <- arbitrary[Method]
      p <- arbitrary[Path]
      c <- arbitrary[ContentType]
    } yield request(m, p, c))
}
