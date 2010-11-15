import sbt._

class Scalatrack(info: ProjectInfo) extends ParentProject(info)
{
  lazy val messages = project("messages", "Messages")
  lazy val actorServer = project("actor_server", "Actor server", messages)
  lazy val webServer = project("web_server", "Web server", actorServer, messages)
}
