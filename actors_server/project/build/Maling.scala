import sbt._

class Maling(info: ProjectInfo) extends DefaultProject(info) with AkkaProject {
  val scalatest = "org.scalatest" % "scalatest" % "1.2"

  
  val messages = "no.bekk.scala" %% "messages" % "1.0.1" 

  override def mainClass = Some("Server")
}