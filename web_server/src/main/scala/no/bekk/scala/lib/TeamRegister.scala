package no.bekk.scala.lib

import no.bekk.scala.Team
import no.bekk.scala.messages._
import no.bekk.scala.{Challenges, Challenge}

object TeamRegisterWithChallenges extends TeamRegister with Challenges

trait TeamRegister extends TeamService
{
  def challenges: List[Challenge];

  private var teamsAnswers : Map[Team, List[(Question, Option[Any])] ]= Map()

  def listTeams = teamsAnswers.keys.toList

  def statusOfQuestionForTeam(team:Team):List[(Question, Option[Any])] =  {
    teamsAnswers.get(team) match {
      case None => List()
      case Some(list) => list
    }
  }

  def registerCompletedChalange(team: Team, question:Question, answer: Option[Any])={
    println("team klarte " + team + " , " + question)
    val teamList = teamsAnswers.get(team) match {
      case None =>
        teamsAnswers = teamsAnswers + ((team, firstAnswer(question, answer)))
      case Some(list) =>
        teamsAnswers = teamsAnswers + ((team, newAnswer(list, question, answer)))
    }
  }

  def registerFaildChallenge(team : Team, question: Question)= {
    println("team klarte ikke spørsmål  " + team + ", "+ question)
    val teamList = teamsAnswers.get(team).get
    teamsAnswers = teamsAnswers + ((team, teamList.map( answer =>{
        if(answer._1.equals(question))
          (question, None)
        else
          answer
      })
    ))
  }

  private def firstAnswer(question: Question, answer:Option[Any]):List[(Question,Option[Any])] = {
    challenges.map( challenge => {
      val q = new Question(challenge.question, challenge.content)
      if(q.equals(question))
        ((question, answer))
      else
        ((q, None))
    })
  }

  private def newAnswer(previouseAnswers:List[(Question,Option[Any])], question: Question, answer: Option[Any]):List[(Question,Option[Any])] =
  {
    previouseAnswers.map(previouseAnswer =>{
      if(previouseAnswer._1.question.equals(question.question))
        ((question, answer))
      else
        previouseAnswer
    })
  }

  def clear(){
    teamsAnswers = Map()
  }
}