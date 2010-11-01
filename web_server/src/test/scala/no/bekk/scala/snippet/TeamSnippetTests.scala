package test.scala.no.bekk.scala.snippet

import org.scalatest.{FlatSpec, BeforeAndAfterEach}
import org.scalatest.matchers.ShouldMatchers
import scala.xml.NodeSeq

import no.bekk.scala._
import no.bekk.scala.messages._
import no.bekk.scala.snippet.TeamSnippet
import no.bekk.scala.lib.TeamService
import net.liftweb.util.BindHelpers._

class TeamSnippetTests  extends FlatSpec with ShouldMatchers with BeforeAndAfterEach {

  val xml =
  <div class="team bar">
    <div class="team header">
        <h2><team:name/></h2>
    </div>
    <team:oppgaver>
        <div oppgave:class="oppgave"><oppgave:status/></div>
    </team:oppgaver>
  </div>;

  "TeamSnippet baren" should "the team name" in {

    val snippet = new TeamSnippet with TestTeamProvider

    val expected = <h2>Team name</h2>

    snippet.status(xml).toString should include(expected.toString)
  }

  it should "contain all tasks" in {
    val snippet = new TeamSnippet with TestTeamProvider

    snippet.status(xml).toString should include("feil")
    snippet.status(xml).toString should include("riktig")

  }

  it should "set class to solved when challenges are solved" in{
    val snippet = new TeamSnippet with TestSuccessTeamProvider

    snippet.status(xml).toString should include("class=\"oppgave solved\"")
    snippet.status(xml).toString should not include("class=\"oppgave failed\"")    

  }

  it should "set class to nothing when challenges are'nt solved" in{
    val snippet = new TeamSnippet with TestFailsTeamProvider

    snippet.status(xml).toString should include("class=\"oppgave failed\"")
  }
}

trait TestTeamProvider
{ self:TeamSnippet =>
  override val teamServices = new TestTeamService((new Question("Riktig", ""), Some("riktig")) ::(new Question("test", ""), None) :: Nil)
}


trait TestFailsTeamProvider
{ self:TeamSnippet =>
  override val teamServices = new TestTeamService((new Question("test", ""), None) :: Nil)
}

trait TestSuccessTeamProvider
{ self:TeamSnippet =>
  override val teamServices = new TestTeamService((new Question("Riktig", ""), Some("riktig")) :: Nil)
}

class TestTeamService(val questions:List[(Question, Option[Any])]) extends TeamService
{
  def listTeams = List(new no.bekk.scala.Team("Team name"))
  def statusOfQuestionForTeam(team:Team):List[(Question, Option[Any])] = questions

}