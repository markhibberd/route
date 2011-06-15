package io.mth.route

sealed trait Route[A] {
  val v: Request => Response[A]
}

object Route {
  def route[A](f: Request => Response[A]): Route[A] = new Route[A] {
    val v = f
  }

  def constant[A](a: A): Route[A] = route(_ => Response.found(a))
}



trait Routes[A] {
  def fold[X](
    route: Route[A] => X, 
    routes: (Route[A], Routes[A]) => X
  ): X
}

object Routes {
  def base[A](route: Route[A]): Routes[A] = new Routes[A] {
    def fold[X](
      r: Route[A] => X, 
      rs: (Route[A], Routes[A]) => X
    ) = r(route)
  }


  implicit def RouteToRoutes[A](r: Route[A]): Routes[A] = base(r)
}



trait Request {
}

trait Response[A] {
  def fold[X](
    found: A => X,
    notfound: => X,
    failure: RoutingFailure => X
  ): X
}

object Response {
  def found[A](a: A): Response[A] = new Response[A] {
    def fold[X](
      found: A => X,
      notfound: => X,
      failure: RoutingFailure => X
    ) = found(a)
  }

  def notfound[A]: Response[A] = new Response[A] {
    def fold[X](
      found: A => X,
      notfound: => X,
      failure: RoutingFailure => X
    ) = notfound
  }

  def failure[A](e: RoutingFailure): Response[A] = new Response[A] {
    def fold[X](
      found: A => X,
      notfound: => X,
      failure: RoutingFailure => X
    ) = failure(e)
  }
}

case class RoutingFailure(message: String)
