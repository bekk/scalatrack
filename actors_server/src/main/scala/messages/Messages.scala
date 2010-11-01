package no.bekk.scala.messages

import _root_.no.bekk.scala.Team
import se.scalablesolutions.akka.actor.ActorRef

trait Message

case class MoreChallenges(val team:Team) extends Message
case class Question(val question:String, val content:Any) extends Message

case class Answer(val temaName:Team, val chalange:Question, val answer: Any) extends Message

trait Verdict
case class Correct() extends Message with Verdict 
case class Wrong() extends Message with Verdict