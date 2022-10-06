import sbt.Keys._

lazy val commonSettings = Seq(
  version := "1.0",
  name := "amqp-reseach",
  scalaVersion := "2.11.8"
)

lazy val commonDependencies = Seq(

  "org.apache.spark" %% "spark-yarn" % "2.3.1", // for remote debug

  "org.apache.qpid" % "qpid-jms-client" % "0.61.0",
  "javax.jms" % "javax.jms-api" % "2.0.1",
  "org.apache.qpid" % "proton-j" % "0.33.10",
  "io.netty" % "netty-handler" % "4.1.72.Final"

)

lazy val root = (project in file(".")).
  settings(commonSettings: _*).
  settings(
    libraryDependencies ++= commonDependencies,
  ).
  enablePlugins(AssemblyPlugin)

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}

assemblyJarName in assembly := "amqp-reseach_2.11-1.0.jar"


