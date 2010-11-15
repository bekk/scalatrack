package test.scala

import _root_.no.bekk.scala._
import _root_.no.bekk.scala.messages._
import org.scalatest.{FlatSpec, BeforeAndAfterEach}
import org.scalatest.matchers.ShouldMatchers
import se.scalablesolutions.akka.actor.Actor._
import se.scalablesolutions.akka.actor._


class ChallengeCollectionTests extends FlatSpec with ShouldMatchers with BeforeAndAfterEach with GiveAnswer {

  object AllChallenges extends Challenges{
    def list = challenges
  }

  override def beforeEach={
    server= actorOf(new Server with Challenges with PrintlineScoreBoardProvider).start
  }

  override def afterEach={
    if(server != null)
      server.stop
  }

  var server : ActorRef  = _
  val team = new Team("test")

  "Our client" should "get all answers correct" in{
    val team = new Team("test")

      Range(0, AllChallenges.list.length).foreach( i =>{
        val option = server !! MoreChallenges(team)

        val question = option.get.asInstanceOf[Question]
        val answer = new Answer(team, question, giveAnswer(question))

        println(question)
        val verdict = (server !! answer)
        verdict should be (Some(Correct()))
      })

  }
}