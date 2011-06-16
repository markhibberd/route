package io.mth.route

trait Routes[A] {
  import Routes._

  def fold[X](
    routes: List[Route[A]] => X
  ): X

  def |(r: Route[A]): Routes[A] = fold(
    rs => compound(rs ::: r :: Nil)
  )
}

object Routes {
  def base[A](route: Route[A]): Routes[A] = new Routes[A] {
    def fold[X](
      routes: List[Route[A]] => X
    ) = routes(route :: Nil)
  }

  def compound[A](rs: List[Route[A]]): Routes[A] = new Routes[A] {
    def fold[X](
      routes: List[Route[A]] => X
    ) = routes(rs)
  }
}

