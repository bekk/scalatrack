package no.bekk.scala.snippet

import _root_.no.bekk.scala.lib.{TeamRegisterWithChallenges, TeamService, TeamRegister}
import scala.xml.NodeSeq
import net.liftweb.util.BindHelpers._
import no.bekk.scala.messages._
import no.bekk.scala.Team
import scala.Option._

class TeamSnippet
{
  val teamServices: TeamService = TeamRegisterWithChallenges

  def textForAnswer(answer:Option[String]) = {
      answer match {
          case None => "feil"
          case Some(_) => "riktig"
        }
  }

  def classForAnswer(answer:Option[String])={
    answer match{
      case None=> "oppgave failed"
      case Some(_) => "oppgave solved"
    }
  }

  def status(seq : NodeSeq) : NodeSeq={
    teamServices.listTeams.flatMap(team => bind("team", seq,
      "name" -> team.name,
      "oppgaver" -> teamServices.statusOfQuestionForTeam(team).flatMap{
          case  ((question, answer)) => {
            bind("oppgave", chooseTemplate("team", "oppgaver", seq),
              "status" -> textForAnswer(answer),
              AttrBindParam("class" , classForAnswer(answer), "class"))
          }
      }
    ))

  }
}