import sbt._

class Scalatrack(info: ProjectInfo) extends ParentProject(info)
{
  val messages = project("messages")
  val actorServer = project("actors_server", messages)
  val webServer = project("web_server", actorServer, messages)
}
