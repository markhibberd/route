import sbt._
import Keys._

object build extends Build {
  type Sett = Project.Setting[_]

  override lazy val settings = super.settings ++
        Seq(resolvers := Seq(
          "mth.io snapshots" at "http://repo.mth.io/snapshots"
        , "mth.io releases" at "http://repo.mth.io/releases"
        , "snapshots" at "http://oss.sonatype.org/content/repositories/snapshots"
        , "releases" at "http://oss.sonatype.org/content/repositories/releases"
        ))

  lazy val publishSetting = publishTo <<= (version).apply{
    v => {
      val flavour = if (v.trim.endsWith("SNAPSHOT")) "snapshots" else "releases"
      Some(Resolver.sftp("repo.mth.io","repo.mth.io", "repo.mth.io/data/snapshots") as ("web", new java.io.File(
          System.getProperty("user.home") + "/.ssh/id_dsa_publish")))
    }
  }

  val route = Project(
    id = "route"
  , base = file(".")
  , settings = Defaults.defaultSettings ++ Seq[Sett](
      name := "route"
    , organization := "io.mth"
    , version := "1.3"
    , scalaVersion := "2.11.5"
    , crossScalaVersions := Seq("2.10.4", scalaVersion.value)
    , scalacOptions := Seq(
        "-deprecation"
      , "-unchecked"
      , "-feature"
      , "-language:_"
      )
    , scalacOptions in (Compile,console) := Seq("-deprecation", "-language:_", "-feature")
    , scalacOptions in (Test,console) := Seq("-deprecation", "-language:_", "-feature")
    , sourceDirectory in Compile <<= baseDirectory { _ / "src" }
    , sourceDirectory in Test <<= baseDirectory { _ / "test" }
    , historyPath <<= baseDirectory { b => Some(b / "gen/sbt/.history") }
    , target <<= baseDirectory { _ / "gen/sbt/target" }
    , testOptions in Test += Tests.Setup(() => System.setProperty("specs2.outDir", "gen/sbt/target/specs2-reports"))
    , publishSetting
    , libraryDependencies ++= Seq(
        ("org.scalaz" %% "scalaz-core" % "7.1.0")
      , ("org.specs2" %% "specs2-core" % "2.4.16" % "test")
      , ("org.specs2" %% "specs2-scalacheck" % "2.4.16" % "test")
      , ("org.scalacheck" %% "scalacheck" % "1.12.2" % "test")
      )
    )
  )
}
