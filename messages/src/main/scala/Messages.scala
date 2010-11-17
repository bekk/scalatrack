package no.bekk.scala.messages

import _root_.no.bekk.scala.Team

trait Message

case class MoreChallenges(val team:Team) extends Message
case class Question(val question:String, val content:Any) extends Message
object IntList
{
  def apply(content:List[Int]) = content.asInstanceOf[Any]
  def unapply(list :Any) = try{
    Some(list.asInstanceOf[List[Int]])
  }catch{
    case _ => None
  }
}

object IntListList
{
  def apply(content:List[List[Int]]) = content.asInstanceOf[Any]
  def unapply(list :Any) = try{
    Some(list.asInstanceOf[List[List[Int]]])
  }catch{
    case _ => None
  }
}

object SymbolList
{
  def apply(content:List[Symbol]) = content.asInstanceOf[Any]
  def unapply(list :Any) = try{
    Some(list.asInstanceOf[List[Symbol]])
  }catch{
    case _ => None
  }
}


case class Answer(val temaName:Team, val question:Question, val answer: Any) extends Message

trait Verdict
case class Correct() extends Message with Verdict 
case class Wrong() extends Message with Verdict