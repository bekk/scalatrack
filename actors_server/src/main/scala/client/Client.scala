package no.bekk.scala

import no.bekk.scala.messages._
import se.scalablesolutions.akka.actor.Actor
import se.scalablesolutions.akka.actor._
import se.scalablesolutions.akka.remote._


trait Publisher
{
  val publisher = RemoteClient.actorFor("Server", "localhost", 9999)
}

abstract class Client
{
  val team:Team
  val publisher: ActorRef;
  def run {
    val remote = publisher

    println("remote er " + remote)

    val chalange = remote !! MoreChallenges(team)
    println("received " + chalange)

    chalange match {
      case Some(x@Question(_)) => remote !! Answer(team, x, "svar")
      case _ => println("hva..")
    }
  }
}

trait TestTeam
{
  val team = new Team("test")
}

object Client extends Application
{
  val client = new Client with Publisher with TestTeam
  client.run
}
