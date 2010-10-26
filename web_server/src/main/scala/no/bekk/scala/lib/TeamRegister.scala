package no.bekk.scala.lib

import no.bekk.scala.Team
import no.bekk.scala.messages._

object TeamRegister extends TeamService
{
  def listTeams = List[no.bekk.scala.Team](new Team("test"))
  def statusOfQuestionForTeam(team:Team):List[(Question, Option[Answer])] = List(
    (new Question("tøyse spørsmål"), None),
    (new Question("tøyse spørsmål"), Some(new Answer(new Team("test"), new Question("="), "Svar")))
  ) 
}