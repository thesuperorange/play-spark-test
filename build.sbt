name := """helloworld"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.6"


libraryDependencies ++= {
  val sparkV = "1.6.2"
  //val hadoopV     = "2.4.0"
  Seq(
    //jdbc,
    cache,
    ws,
    //"mysql" % "mysql-connector-java" % "5.1.34",

    "com.databricks" % "spark-csv_2.10" % "1.4.0",
    "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.0-RC1" % Test,
    "org.apache.spark" %% "spark-core" % sparkV,
    "org.apache.spark" %% "spark-mllib" % sparkV,
    "org.apache.spark" %% "spark-sql" % sparkV,
    "org.apache.spark" %% "spark-graphx" % sparkV
  )
}
dependencyOverrides ++= Set(
  "com.fasterxml.jackson.core" % "jackson-databind" % "2.4.4"
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"


fork in run := true