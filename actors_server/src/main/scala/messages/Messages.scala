package no.bekk.scala.messages

import _root_.no.bekk.scala.Team
import se.scalablesolutions.akka.actor.ActorRef
trait Message

case class MoreChalanges(val team:Team) extends Message
case class Question(val question:String) extends Message

case class Answer(val temaName:Team, val chalange:Question, val answer: String) extends Message
case class Correct()
case class Wrong() 