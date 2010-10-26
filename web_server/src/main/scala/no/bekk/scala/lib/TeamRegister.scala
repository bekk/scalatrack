package no.bekk.scala.lib

import no.bekk.scala.Team
import no.bekk.scala.messages._

object TeamRegister extends TeamService
{
  def listTeams = List[no.bekk.scala.Team]()
  def statusOfQuestionForTeam(team:Team):List[(Question, Option[Answer])] = List((new Question("tøyse spørsmål"), None)) 
}