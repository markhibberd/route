package io.mth.route

sealed trait Path {
  import Path._
  def fold[X](f: List[String] => X): X

  def </>(req: Path): Path = new Path {
    val combined = Path.this.fold(p1 => req.fold(p2 => p1 ++ p2))
    def fold[X](f: List[String] => X) = {
      f(combined)
    }
  }

  def head: Option[Path] = fold(_.headOption.map(Path.part(_)))

  def tail: Path = fold(_.tail.foldLeft(base)((acc, v) => acc </> Path.part(v)))

  def init: Path = fold(_.init.foldLeft(base)((acc, v) => acc </> Path.part(v)))

  def last: Option[Path] = fold(_.lastOption.map(Path.part(_)))

  def lastForInit(path: Path): Option[Path] = if (init == path) last else None

  override def toString =
    fold(_.mkString("/"))

  override def equals(obj: Any) =
    obj.isInstanceOf[Path] &&
    obj.asInstanceOf[Path].fold(p1 => fold(p2 => p1 == p2))
}

object Path {
  def base: Path = new Path {
    def fold[X](f: List[String] => X) = f(Nil)
  }

  def part(s: String): Path = new Path {
    def fold[X](f: List[String] => X) = f(s :: Nil)
  }

  def parse(s: String): Path = s.split('/').foldLeft(base)((acc, v) => acc </> part(v))
}
