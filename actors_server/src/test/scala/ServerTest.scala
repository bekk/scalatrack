import _root_.messages._
import org.scalatest.{FlatSpec, BeforeAndAfterEach}
import org.scalatest.matchers.ShouldMatchers
import se.scalablesolutions.akka.actor.Actor._
import se.scalablesolutions.akka.actor._

trait TestChalanges
{
  val chalanges = Map("chalange" -> "answer", "chalange2" -> "answer")
}

class ServerTest extends FlatSpec with ShouldMatchers with BeforeAndAfterEach {

  var server:ActorRef = null
  
  override def beforeEach={
    server = actorOf(new Server with TestChalanges).start    
  }

  "Our server" should "give a chalange when you ask for one" in {
    server = actorOf(new Server with Chalanges).start
    val chalange = server !! MoreChalange()

    chalange should be (Some(Chalange("tester")))
  }

  it should "give diffrent chalanges" in {
    val chalange = server !! MoreChalange()
    chalange should be (Some(Chalange("chalange")))

    val chalange2 = server !! MoreChalange()
    chalange2 should be (Some(Chalange("chalange2")))
  }

  it should "return wrong it answered wrong" in {
    val chalange = server !! MoreChalange()
    val answerStatus = server !! Answer("team", chalange.get.asInstanceOf[Chalange], "wrong answer")

    answerStatus should be (Some(Wrong()))
  }

  it should "return correct if answered correctly" in {
    val chalange = server !! MoreChalange()
    val answerStatus = server !! Answer("team", chalange.get.asInstanceOf[Chalange], "answer")

    answerStatus should be (Some(Correct()))
  }

}
