package test.scala


import _root_.no.bekk.scala._
import _root_.no.bekk.scala.messages._
import org.scalatest.{FlatSpec, BeforeAndAfterEach}
import org.scalatest.matchers.ShouldMatchers
import se.scalablesolutions.akka.actor.Actor._
import se.scalablesolutions.akka.actor._


class ChallengeTests extends FlatSpec with ShouldMatchers with BeforeAndAfterEach{

  "A question" should "carry a payload" in{
    val question = new Question("Siste element i listen", List(1,2,3,4,5))

    question.content should be (List(1,2,3,4,5))
  }
}


class AdvancedChallengeTests extends FlatSpec with ShouldMatchers with BeforeAndAfterEach{

  var server:ActorRef = null
  val team = new Team("test")

  override def beforeEach={
    server = actorOf(new Server with AdvancedChallenges with PrintlineScoreBoardProvider).start
  }

  "A advanced question" should "be interpetret" in{

    val challenge = server !! MoreChallenges(team)

    val verdict = challenge.get match{
      case Question(_, content:List[int]) => {
        server !! Answer(team, challenge.get.asInstanceOf[Question], content.last)  
      }
      case _ => fail()
    }

    verdict.get should be (Correct())
  }

  trait AdvancedChallenges
  {
    val challenges = List(
      new Challenge("last","", List(1,2,3), (number) => number == 3)
    )
  }
}