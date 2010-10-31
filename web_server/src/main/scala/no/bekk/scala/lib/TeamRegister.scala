package no.bekk.scala.lib

import no.bekk.scala.Team
import no.bekk.scala.messages._
import no.bekk.scala.{Challenges, Challenge}

object TeamRegisterWithChallenges extends TeamRegister with Challenges

trait TeamRegister extends TeamService
{
  val challenges: List[Challenge];

  private var register : Map[Team, List[(Question, Option[String])] ]= Map()

  def listTeams = register.keys.toList

  def statusOfQuestionForTeam(team:Team):List[(Question, Option[String])] = register.get(team) match {
    case None => List()
    case Some(list) => list
  }

  private def onlyAnswered(question: Question, answer:Option[String]):List[Tuple2[Question,Option[String]]] = {
    challenges.map( challenge => {
      val q = new Question(challenge.question)
      if(q.equals(question))
        ((question, answer))
      else
        ((q, None))
    })
  }

  private def newAnswer(previouseAnswers:List[(Question,Option[String])], question: Question, answer: Option[String]):List[(Question,Option[String])] =
  {
    previouseAnswers.map(previouseAnswer =>{
      if(previouseAnswer._1.equals(question))
        ((question, answer))
      else
        previouseAnswer
    })
  }

  def registerCompletedChalange(team: Team, question:Question, answer: Option[String])={
    val teamList = register.get(team) match {
      case None =>
        register = register + ((team, onlyAnswered(question, answer)))
      case Some(list) =>
        register = register+ ((team, newAnswer(list, question, answer)))
    }
  }

  def clear(){
    register = Map()
  }
}