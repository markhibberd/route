package io.mth.route

sealed trait Funnel[A] {
  def fold[B, X](base: A => X, compound: (RequestSignature => Option[A], Funnel[A]) => X): X

  def :>:(f: RequestSignature => Option[A]): Funnel[A] = new Funnel[A] {
    def fold[B, X](base: A => X, compound: (RequestSignature => Option[A], Funnel[A]) => X) =
      compound(f, Funnel.this)
  }

  def apply(request: RequestSignature): A =
    fold(x => x, (f, rest) => f(request).getOrElse(rest(request)))
}

object Funnel {
  def base[A](a: => A): Funnel[A] = new Funnel[A] {
     def fold[B, X](base: A => X, compound: (RequestSignature => Option[A], Funnel[A]) => X) =
      base(a)
  }

  def rule[A](f: RequestSignature => Option[A]): RequestSignature => Option[A] = f

  def is[A](m: Methods, p: Path, a: => A) = rule(s => for {
    _ <- m.find(s.method)
    b <- if (p == s.path) Some(a) else None
  } yield b)

  def id[A](m: Methods, p: Path, a: String => A) = rule(s => for {
    _ <- m.find(s.method)
    b <- s.path.lastForInit(p).map(z => a(z.toString))
  } yield b)
}
