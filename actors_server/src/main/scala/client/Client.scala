package no.bekk.scala

import no.bekk.scala.messages._
import scala.actors.Actor.loop
import se.scalablesolutions.akka.actor.Actor
import se.scalablesolutions.akka.actor._
import se.scalablesolutions.akka.remote._


trait PublisherProvider
{
  val publisher = RemoteClient.actorFor("Server", "localhost", 9999)
}

trait TestTeamProvider
{
  val team = new Team("test")
}

abstract class Client
{
  val team:Team
  val publisher: ActorRef
  
  def run {
    val remote = publisher

    remote !! MoreChallenges(team) match {
      case Some(x@Question(_, list:List[Int])) => remote !! Answer(team, x, list.last)
      case Some(x@Question(_, _)) => {
         val correct_? = remote !! Answer(team, x, "pong")
       println(correct_?)
     }
       case x@None => println("hva ... " + x)
    }
  }
}

object Client extends Application
{
  val client = new Client with PublisherProvider with TestTeamProvider
  client.run
}
