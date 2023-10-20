import sbt.Keys.resolvers

ThisBuild / organization := "dev.gigapixel"
ThisBuild / scalaVersion := "3.3.1"

val http4sVersion = "0.23.23"
val LogbackVersion = "1.4.11"
lazy val root = (project in file(".")).settings(
  name := "tokenservice",
  resolvers += "Local Maven Repository" at "file://" + Path.userHome.absolutePath + "/.m2/repository",
  resolvers += "Jena bio" at "https://bio.informatik.uni-jena.de/repository/libs-release-oss/",
  libraryDependencies ++= Seq(
    // "core" module - IO, IOApp, schedulers
    // This pulls in the kernel and std modules automatically.
    "org.typelevel" %% "cats-effect" % "3.5.2",
    // concurrency abstractions and primitives (Concurrent, Sync, Async etc.)
    "org.typelevel" %% "cats-effect-kernel" % "3.5.2",
    "dev.gigapixel" % "tok4j" % "1.0-SNAPSHOT",
    "commons-io" % "commons-io" % "2.14.0",
    // standard "effect" library (Queues, Console, Random etc.)
    "org.typelevel" %% "cats-effect-std" % "3.5.2",
    "org.typelevel" %% "munit-cats-effect-3" % "1.0.7" % Test,
    "io.circe" %% "circe-core" % "0.14.6",
    "io.circe" %% "circe-core" % "0.14.6",
    "org.typelevel" %% "cats-core" % "2.10.0",
    "org.http4s" %% "http4s-ember-client" % http4sVersion,
    "org.http4s" %% "http4s-ember-server" % http4sVersion,
    "org.http4s" %% "http4s-dsl" % http4sVersion,
    "org.http4s" %% "http4s-circe" % http4sVersion,
    "org.scalameta" % "svm-subs" % "101.0.0",
    "ch.qos.logback" % "logback-classic" % LogbackVersion % Runtime
  )
)
