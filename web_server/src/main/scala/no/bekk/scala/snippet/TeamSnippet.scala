package no.bekk.scala.snippet

import _root_.no.bekk.scala.lib.{TeamService, TeamRegister}
import scala.xml.NodeSeq
import net.liftweb.util.BindHelpers._
import no.bekk.scala.messages._
import no.bekk.scala.Team
import scala.Option._

class TeamSnippet
{
  val teamServices: TeamService = TeamRegister 

  def textForAnswer(answer:Option[Answer]) = {
      answer match {
          case None => "feil"
          case Some(_) => "riktig"
        }
  }

  def status(seq : NodeSeq) : NodeSeq={
   
    teamServices.listTeams.flatMap(team => bind("team", seq,
      "name" -> team.name,
      "oppgaver" -> teamServices.statusOfQuestionForTeam(team).flatMap{
          case  ((question, answer)) => bind("oppgave", chooseTemplate("team", "oppgaver", seq), "status" -> textForAnswer(answer) )
      }
    ))

  }
}