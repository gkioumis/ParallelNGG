name := "ParallelNGG"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "1.6.0" % "provided",
  "org.apache.spark" %% "spark-mllib" % "1.6.0",
  "org.apache.spark" %% "spark-graphx" % "1.6.0",
  "org.apache.opennlp" % "opennlp-tools" % "1.6.0",
  "org.scalatest" % "scalatest_2.10" % "2.0" % "test"
)

