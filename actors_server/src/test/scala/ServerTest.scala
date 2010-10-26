
import _root_.no.bekk.scala._
import _root_.no.bekk.scala.messages._
import org.scalatest.{FlatSpec, BeforeAndAfterEach}
import org.scalatest.matchers.ShouldMatchers
import se.scalablesolutions.akka.actor.Actor._
import se.scalablesolutions.akka.actor._

trait TestChallenges
{
  val challenges = List(
    new Challenge("challenge", "answer"),
    new Challenge("challenge2", "answer")
  )
}

class ServerTest extends FlatSpec with ShouldMatchers with BeforeAndAfterEach {

  var server:ActorRef = null
  val team = new Team("test")
  
  override def beforeEach={
    server = actorOf(new Server with TestChallenges with PrintlineScoreBoardService).start
  }

  "As a client the server" should "give a challenge when you ask for one" in {
    server = actorOf(new Server with Challenges with PrintlineScoreBoardService).start
    val chalange = server !! MoreChallenges(team)

    chalange should be (Some(Question("tester")))
  }

  it should "give diffrent challenges" in {
    val challenge = server !! MoreChallenges(team)
    challenge should be (Some(Question("challenge")))

    val challenge2 = server !! MoreChallenges(team)
    challenge2 should be (Some(Question("challenge2")))
  }

  it should "repeate the diffrent challenges" in{
    server !! MoreChallenges(team) should not be (None)
    server !! MoreChallenges(team) should not be (None)
    val firstQuestion = Some(new Question("challenge"))
    server !! MoreChallenges(team) should equal(firstQuestion)
  }

  it should "return wrong it answered wrong" in {
    val chalange = server !! MoreChallenges(team)
    val answerStatus = server !! Answer(team, chalange.get.asInstanceOf[Question], "wrong answer")

    answerStatus should be (Some(Wrong()))
  }

  it should "return correct if answered correctly" in {
    val chalange = server !! MoreChallenges(team)
    val answerStatus = server !! Answer(team, chalange.get.asInstanceOf[Question], "answer")

    answerStatus should be (Some(Correct()))
  }

  it should "give all challenges to all clients in turn" in {
    var chalangeToOne = server !! MoreChallenges(new Team("One"))
    var chalangeToTwo = server !! MoreChallenges(new Team("Two"))

    chalangeToOne should equal(chalangeToTwo)

    chalangeToOne = server !! MoreChallenges(new Team("One"))
    chalangeToTwo = server !! MoreChallenges(new Team("Two"))

    chalangeToOne should equal(chalangeToTwo)
  }

  it should "update the score board" in{
    var scoreBoardTeam:Team = null;
    var scoreBoardChalange: Challenge = null
    class TestScoreBoard extends ScoreBoardService
    {
      def chalangeCompleted(teamet: no.bekk.scala.Team, chalange:no.bekk.scala.Challenge) = {
        scoreBoardTeam = teamet;
        scoreBoardChalange = chalange;
      }
    }
    trait TestScoreBoardService{
      val scoreBoard = new TestScoreBoard
    }

    server = actorOf(new Server with TestChallenges with TestScoreBoardService).start
    var chalange = server !! MoreChallenges(team)
    server !! Answer(team, chalange.get.asInstanceOf[Question], "answer")

    scoreBoardTeam should equal(team)
    scoreBoardChalange should equal(new Challenge(chalange.get.asInstanceOf[Question].question, "answer"))
  }



}
