name := "play2-sync-mail-plugin"

organization := "play.modules.mail"

version := "0.1-SNAPSHOT"

resolvers += "Typesafe repository snapshots" at "http://repo.typesafe.com/typesafe/snapshots/"

resolvers += "Typesafe repository releases" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies ++= Seq(
  "org.codemonkey.simplejavamail" % "simple-java-mail" % "2.1",
  "com.typesafe.play" %% "play" % "2.2.1",
  "com.typesafe.play" %% "play-test" % "2.2.1",
  "org.specs2" %% "specs2" % "1.7.1" % "test",
  "junit" % "junit" % "4.8" % "test"
)
