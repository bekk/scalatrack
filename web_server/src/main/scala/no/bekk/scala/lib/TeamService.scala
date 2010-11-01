package no.bekk.scala.lib

import no.bekk.scala.Team
import no.bekk.scala._
import no.bekk.scala.messages._

trait TeamService
{
  def listTeams: List[Team];
  def statusOfQuestionForTeam(team:Team):List[(Question, Option[Any])];
}