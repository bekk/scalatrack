


package no.bekk.scala.comet

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

  def registerWith = LeretServer
	
	// The following specifies a default prefix
	override def defaultPrefix = Full("msg") 
	
	// Intial bindings
	def render = bind("message" -> <span id="message">test</span>)
	
	// this is called 10sec after the instance is created

  RemoteNode.start("localhost", 9999)
  RemoteNode.register("Server", Actor.actorOf(new Server with Challenges with CometServerScoreboardProvider))
	
	override def lowPriority: PartialFunction[Any,Unit] = {
    case m: List[CompletedChallenge] => partialUpdate(SetHtml("message", <span>hei sveis : {m}</span>))

    case x@_ => println("frostår ingen ting " + x)
	}
}
case object Message
case class CompletedChallenge(val team:Team, val chalange: Challenge )


object LeretServer extends LiftActor with ListenerManager {
   private var messages = List[CompletedChallenge]()

   def createUpdate = messages

   override def lowPriority = {
    case completedChallenge@CompletedChallenge(_,_)=> {
      messages ::= completedChallenge 
      updateListeners()
    }
   }
}

trait CometServerScoreboardProvider 
{
  val scoreBoard = new CometServerScoreboardService
}

class CometServerScoreboardService extends ScoreBoardService
{
  def chalangeCompleted(team: Team, challenge: Challenge) = {
    LeretServer ! new CompletedChallenge(team, challenge)
  }
}