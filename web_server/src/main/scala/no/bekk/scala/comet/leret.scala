


package no.bekk.scala.comet

import _root_.no.bekk.scala.lib.{CometServerScoreboardProvider, CompletedChallenge}
import net.liftweb._
import http._
import SHtml._ 
import net.liftweb.common.{Box, Full}
import net.liftweb.util._
import net.liftweb.actor._
import net.liftweb.util.Helpers._
import net.liftweb.http.js.JsCmds.{SetHtml}
import net.liftweb.http.js.JE.Str
import no.bekk.scala._
import se.scalablesolutions.akka.actor._
import se.scalablesolutions.akka.remote._

class Leret extends CometActor with CometListener {

  startAkkaServer

  def registerWith = LeretServer

	override def defaultPrefix = Full("msg") 
	
	def render = bind("message" -> <span id="message">test</span>)
	

  def startAkkaServer ={
    RemoteNode.start("localhost", 9999)
    RemoteNode.register("Server", Actor.actorOf(new Server with Challenges with CometServerScoreboardProvider))
  }

	override def lowPriority: PartialFunction[Any,Unit] = {
    case m: List[CompletedChallenge] => partialUpdate(SetHtml("message", <span>hei sveis : {m}</span>))
	}
}

object LeretServer extends LiftActor with ListenerManager {
   private var completedChallenges = List[CompletedChallenge]()

   def createUpdate = completedChallenges

   override def lowPriority = {
    case completedChallenge@CompletedChallenge(_,_)=> {
      completedChallenges ::= completedChallenge
      updateListeners()
    }
   }
}
