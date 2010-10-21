package messages

import se.scalablesolutions.akka.actor.ActorRef
trait Message

case class MoreChalange() extends Message
case class Chalange(val question:String) extends Message
case class Response(val chalange : Chalange, val teamName : String, val message:String) extends Message

case class Answer(val temaName:String, val chalange:Chalange, val answer: String) extends Message
case class Correct()
case class Wrong() 