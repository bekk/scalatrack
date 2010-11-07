package no.bekk.scala.comet

import _root_.no.bekk.scala.lib.{CometServerScoreboardProvider, ChangesToTheScoreboard}
import net.liftweb._
import http._
import scala.xml._
import SHtml._ 
import net.liftweb.common.{Box, Full}
import net.liftweb.util._
import net.liftweb.actor._
import net.liftweb.util.Helpers._
import net.liftweb.http.js.JsCmds.{SetHtml}
import net.liftweb.http.js.JE.Str
import no.bekk.scala._
import no.bekk.scala.messages._
import se.scalablesolutions.akka.actor._
import se.scalablesolutions.akka.remote._

import java.util.Date

class Leret extends CometActor with CometListener {

  startAkkaServer

  def render = bind("nothing" -> "test")

  def registerWith = LeretServer

  def startAkkaServer ={
    RemoteNode.start("localhost", 9999)
    RemoteNode.register("Server", Actor.actorOf(new Server with Challenges with CometServerScoreboardProvider))
  }

	override def lowPriority: PartialFunction[Any,Unit] = {
    case _ => reRender(false)
	}
}

object LeretServer extends LiftActor with ListenerManager {

   def createUpdate = List()

   override def lowPriority = {
    case _=> {
      updateListeners()
    }
   }
}
