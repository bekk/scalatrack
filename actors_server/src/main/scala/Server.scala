import _root_.messages._
import _root_.no.bekk.scala._
import se.scalablesolutions.akka.actor._
import se.scalablesolutions.akka.remote._

abstract class Server extends Actor{
  val chalanges: List[Chalange];
  var teamChalanges = Map[Team, Int]();

  val scoreBoard : ScoreBoardService;
  
  private def nextChalange(team:Team) = {
    teamChalanges.get(team) match {
      case None => {
        val next = chalanges(0)
        teamChalanges = teamChalanges + ((team, 1))
        next
      }
      case Some(chalange) => {
        val next = chalanges(chalange)
        teamChalanges = teamChalanges + ((team, chalange +1))
        next
      }
    }

  }

  def handleAnswer(team: Team, chalange:Question, answer:String)={
    val answeredChalange: Chalange = new no.bekk.scala.Chalange(chalange.question, answer)
    if(chalanges.exists(_.equals(answeredChalange))){
      scoreBoard.chalangeCompleted(team, answeredChalange)
      Correct()
    } else
      Wrong()
  }

  def receive={
    case x@MoreChalanges(team) => self.reply(Question(nextChalange(team).question))
    case Answer(team, chalange, answer) => self.reply(handleAnswer(team, chalange, answer)) 
  }
}


object Server extends Application{
  RemoteNode.start("localhost", 9999)
  RemoteNode.register("Server", Actor.actorOf(new Server with Chalanges with PrintlineScoreBoardService))
}