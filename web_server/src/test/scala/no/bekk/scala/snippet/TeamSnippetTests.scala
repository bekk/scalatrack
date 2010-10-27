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

  it should "set class to solved" in{
    val snippet = new TeamSnippet with TestTeamProvider

    snippet.status(xml).toString should include("class=\"oppgave solved\"")
  }
}

trait TestTeamProvider
{ self:TeamSnippet =>
  override val teamServices = new TestTeamService
}

class TestTeamService extends TeamService
{
  def listTeams = List(new no.bekk.scala.Team("Team name"))
  def statusOfQuestionForTeam(team:Team):List[(Question, Option[Answer])] =
    (new Question("test"), None) ::(new Question("Riktig"), Some(new Answer(new Team("test"), new Question("?"), "riktig"))) :: Nil
}