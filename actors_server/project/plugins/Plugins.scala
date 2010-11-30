import sbt._

class Plugins(info: ProjectInfo) extends PluginDefinition(info) {
  val mirror = "mirror" at "http://mirrors.ibiblio.org/pub/mirrors/maven2/"
  val multiverse = "org.multiverse" % "multiverse-alpha" % "0.6" from "http://mirrors.ibiblio.org/pub/mirrors/maven2/org/multiverse/multiverse-alpha/0.6/multiverse-alpha-0.6.jar"
  val akkaPlugin = "se.scalablesolutions.akka" % "akka-sbt-plugin" % "0.10"
}