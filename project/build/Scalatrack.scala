import sbt._

class Scalatrack(info: ProjectInfo) extends ParentProject(info)
{
  override def shouldCheckOutputDirectories = false

  lazy val messages = project("messages", "Messages")
  lazy val actorServer = project("actor_server", "Actor server", messages) 
  lazy val webServer = project("web_server", "Web server", new LiftProject(_), actorServer, messages) 

  class LiftProject(info: ProjectInfo) extends DefaultWebProject(info) with AkkaProject {
    val mavenLocal = "Local Maven Repository" at
    "file://"+Path.userHome+"/.m2/repository"

     val scalatoolsSnapshot = "Scala Tools Snapshot" at
    "http://scala-tools.org/repo-snapshots/"

    val scalatoolsRelease = "Scala Tools Snapshot" at
    "http://scala-tools.org/repo-releases/"

    val liftVersion = "2.1"

    val scalatest = "org.scalatest" % "scalatest" % "1.2" % "test"

    override def libraryDependencies = Set(
      "net.liftweb" %% "lift-webkit" % liftVersion % "compile->default",
      "net.liftweb" %% "lift-testkit" % liftVersion % "compile->default",
      "net.liftweb" %% "lift-wizard" % liftVersion % "compile->default",
      "net.liftweb" %% "lift-mapper" % liftVersion % "compile->default",
      "com.h2database" % "h2" % "1.2.138",
      "org.mortbay.jetty" % "jetty" % "6.1.22" % "test->default",
      "junit" % "junit" % "4.5" % "test->default",
      "org.scala-tools.testing" % "specs" % "1.6.1" % "test->default"
    ) ++ super.libraryDependencies
  }
}
