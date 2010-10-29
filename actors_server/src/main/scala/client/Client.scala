package no.bekk.scala

import no.bekk.scala.messages._
import scala.actors.Actor.loop
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

    while(true){
       remote !! MoreChallenges(team) match {
         case Some(x@Question(_)) => {
           println("question " + x)
           println(remote !! Answer(team, x, "svar"))
         }
         case x@None => println("hva ... " + x)
       }
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
