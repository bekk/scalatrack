import _root_.messages._
import se.scalablesolutions.akka.actor._
import se.scalablesolutions.akka.remote._

abstract class Server extends Actor{
  val chalanges: Map[String, String];

  private var chalange = 0
  private def nextChalange = {
    val next = chalanges.toList(chalange)
    chalange = chalange + 1
    next
  }

  def handleAnswer(chalange:Chalange, answer:String)={
    chalanges.get(chalange.question) match
    {
      case Some(`answer`) => Correct()
      case _ => Wrong()      
    }
  }

  def receive={
    case x@MoreChalange() => self.reply(Chalange(nextChalange._1))
    case x:Response => Correct()
    case Answer(_, chalange, answer) => self.reply(handleAnswer(chalange, answer)) 
  }
}


object Server extends Application{
  RemoteNode.start("localhost", 9999)
  RemoteNode.register("Server", Actor.actorOf(new Server with Chalanges))
}