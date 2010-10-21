import messages.{Response, Chalange, MoreChalange}
import se.scalablesolutions.akka.actor.Actor
import se.scalablesolutions.akka.actor._
import se.scalablesolutions.akka.remote._


trait Publisher
{
  val publisher = RemoteClient.actorFor("Server", "localhost", 9999)
}

abstract class Client
{
  val publisher: ActorRef;
  def run {
    val remote = publisher

    println("remote er " + remote)

    val chalange = remote !! MoreChalange()
    println("received " + chalange)

    chalange match {
      case Some(x@Chalange(_)) => remote !! Response(x, "tull", "hei")
      case _ => println("hva..")
    }

  }
}

object Client extends Application
{
  val client = new Client with Publisher
  client.run
}
