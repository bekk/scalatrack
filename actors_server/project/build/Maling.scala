import sbt._

class Maling(info: ProjectInfo) extends DefaultProject(info) with AkkaProject {
  val scalatest = "org.scalatest" % "scalatest" % "1.2"
}