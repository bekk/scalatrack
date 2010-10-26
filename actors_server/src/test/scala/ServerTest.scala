import _root_.messages._
import _root_.no.bekk.scala._

import _root_.no.bekk.scala.messages._
import org.scalatest.{FlatSpec, BeforeAndAfterEach}
import org.scalatest.matchers.ShouldMatchers
import se.scalablesolutions.akka.actor.Actor._
import se.scalablesolutions.akka.actor._

trait TestChalanges
{
  val chalanges = List(
    new no.bekk.scala.Chalange("chalange", "answer"),
    new no.bekk.scala.Chalange("chalange2", "answer")
  )
}

class ServerTest extends FlatSpec with ShouldMatchers with BeforeAndAfterEach {

  var server:ActorRef = null
  val team = new Team("test")
  
  override def beforeEach={
    server = actorOf(new Server with TestChalanges with PrintlineScoreBoardService).start    
  }

  "As a client the server" should "give a chalange when you ask for one" in {
    server = actorOf(new Server with Chalanges with PrintlineScoreBoardService).start
    val chalange = server !! MoreChalanges(team)

    chalange should be (Some(Question("tester")))
  }

  it should "give diffrent chalanges" in {
    val chalange = server !! MoreChalanges(team)
    chalange should be (Some(Question("chalange")))

    val chalange2 = server !! MoreChalanges(team)
    chalange2 should be (Some(Question("chalange2")))
  }

  it should "return wrong it answered wrong" in {
    val chalange = server !! MoreChalanges(team)
    val answerStatus = server !! Answer(team, chalange.get.asInstanceOf[Question], "wrong answer")

    answerStatus should be (Some(Wrong()))
  }

  it should "return correct if answered correctly" in {
    val chalange = server !! MoreChalanges(team)
    val answerStatus = server !! Answer(team, chalange.get.asInstanceOf[Question], "answer")

    answerStatus should be (Some(Correct()))
  }

  it should "give all chalanges to all clients in turn" in {
    var chalangeToOne = server !! MoreChalanges(new Team("One"))
    var chalangeToTwo = server !! MoreChalanges(new Team("Two"))

    chalangeToOne should equal(chalangeToTwo)

    chalangeToOne = server !! MoreChalanges(new Team("One"))
    chalangeToTwo = server !! MoreChalanges(new Team("Two"))

    chalangeToOne should equal(chalangeToTwo)
  }

  it should "update the score board" in{
    var scoreBoardTeam:Team = null;
    var scoreBoardChalange: Chalange = null
    class TestScoreBoard extends ScoreBoardService
    {
      def chalangeCompleted(teamet: no.bekk.scala.Team, chalange:no.bekk.scala.Chalange) = {
        scoreBoardTeam = teamet;
        scoreBoardChalange = chalange;
      }
    }
    trait TestScoreBoardService{
      val scoreBoard = new TestScoreBoard
    }

    server = actorOf(new Server with TestChalanges with TestScoreBoardService).start 
    var chalange = server !! MoreChalanges(team)
    server !! Answer(team, chalange.get.asInstanceOf[Question], "answer")

    scoreBoardTeam should equal(team)
    scoreBoardChalange should equal(new Chalange(chalange.get.asInstanceOf[Question].question, "answer"))
  }
}
