package no.bekk.scala.lib

import org.scalatest.{FlatSpec, BeforeAndAfterEach}
import org.scalatest.matchers.ShouldMatchers
import scala.xml.NodeSeq

import no.bekk.scala._
import no.bekk.scala.messages._
import no.bekk.scala.snippet.TeamSnippet
import net.liftweb.util.BindHelpers._

class TeamRegisterTests extends FlatSpec with ShouldMatchers with BeforeAndAfterEach {
  val team = new Team("test")

  "Team register" should "keep track of partisipating teams" in {

    TeamRegister.registerCompletedChalange(team, new Question("q"), Some("a"))
    TeamRegister.statusOfQuestionForTeam(team) should be( List((new Question("q"), Some("a"))) )
  }

  it should "keep track of multiple answers" in {
    TeamRegister.registerCompletedChalange(team, new Question("a"), Some("a"))
    TeamRegister.registerCompletedChalange(team, new Question("b"), Some("b"))

    TeamRegister.statusOfQuestionForTeam(team) should be(
        (new Question("a"), Some("a")) :: (new Question("b"), Some("b")) :: Nil
      )
  }

  it should "not keep more than one question per team" in {
    TeamRegister.registerCompletedChalange(team, new Question("a"), Some("a"))
    TeamRegister.registerCompletedChalange(team, new Question("a"), Some("a"))

    TeamRegister.statusOfQuestionForTeam(team) should be(
        (new Question("a"), Some("a")) :: Nil
      )
  }

  override def afterEach() = TeamRegister.clear 
}