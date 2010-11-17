package no.bekk.scala.messages

import _root_.no.bekk.scala.Team

trait Message

case class MoreChallenges(val team:Team) extends Message
case class Question(val question:String, val content:Any) extends Message

trait TypeExtractor[T]
{
  def apply(content:T) = content.asInstanceOf[Any]
  def unapply(list :Any) = try{
    Some(list.asInstanceOf[T])
  }catch{
    case _ => None
  }
}
object IntList extends TypeExtractor[List[Int]]
object IntListList extends TypeExtractor[List[List[Int]]]
object SymbolList extends TypeExtractor[List[Symbol]]


case class Answer(val temaName:Team, val question:Question, val answer: Any) extends Message

trait Verdict
case class Correct() extends Message with Verdict 
case class Wrong() extends Message with Verdict