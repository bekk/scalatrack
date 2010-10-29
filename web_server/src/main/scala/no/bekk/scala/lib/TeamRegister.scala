package no.bekk.scala.lib

import no.bekk.scala.Team
import no.bekk.scala.messages._

object TeamRegister extends TeamService
{
  private var register : Map[Team, List[(Question, Option[String])] ]= Map()

  def listTeams = List[no.bekk.scala.Team](new Team("test"))
  def statusOfQuestionForTeam(team:Team):List[(Question, Option[String])] = register.get(team) match {
    case None => List()
    case Some(list) => list
  }

  def registerCompletedChalange(team: Team, question:Question, answer: Option[String])={
    val teamList = register.get(team) match {
      case None => register = register + ((team, List((question, answer)) ))
      case Some(list) =>register = register+ ((team, list.filterNot(_._1.equals(question))  ++ List(((question, answer))) ))
    }
  }

  def clear(){
    register = Map()
  }
}