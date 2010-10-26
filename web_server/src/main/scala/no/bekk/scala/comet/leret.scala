


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
  RemoteNode.register("Server", Actor.actorOf(new Server with Chalanges with CometServerScoreboardService))
	
	override def lowPriority: PartialFunction[Any,Unit] = {
		case Message => {
      println("ping message received")
			partialUpdate(SetHtml("message", Str(timeNow.toString)))
			ActorPing.schedule(this, Message, 1000L)
		}

    case m: List[String] => partialUpdate(SetHtml("message", <span>hei sveis : {m}</span>))

    case x@_ => println("frostår ingen ting " + x)
	}
}
case object Message


object LeretServer extends LiftActor with ListenerManager {
   private var messages = List("Welcome")

   def createUpdate = messages

   override def lowPriority = {
    case s: String => messages ::= s ; updateListeners()
   }
}

trait CometServerScoreboardService 
{
  val scoreBoard = new CometServerScoreboard
}

class CometServerScoreboard extends ScoreBoardService
{
  def chalangeCompleted(team: Team, chalange: Chalange) = {
    val msg = "team " + team + " solved " + chalange
    println(msg)
    LeretServer ! msg
  }
}