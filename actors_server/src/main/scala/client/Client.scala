package no.bekk.scala

import no.bekk.scala.messages._
import scala.actors.Actor.loop
import se.scalablesolutions.akka.actor.Actor
import se.scalablesolutions.akka.actor._
import se.scalablesolutions.akka.remote._


class Client extends GiveAnswer
{
  val team = new Team("test")
  val remote = RemoteClient.actorFor("Server", "localhost", 9999)
  
  def run {

    while(true)
    {
      val question = (remote !! MoreChallenges(team)).get.asInstanceOf[Question]
      remote !! new Answer(team, question, giveAnswer(question) )
    }
  }

}

trait GiveAnswer {
  def giveAnswer(question : Question):Any = {
    val OppgaveTolker = "^([^ ]+).*".r

    question match {
        case x@Question("Ping", "") =>  "pong"
        case x@Question(OppgaveTolker("Talet"), _) =>  4
        case x@Question(OppgaveTolker("P-01:"), IntList(list)) =>  list.last
        case x@Question(OppgaveTolker("P-02:"), IntList(list)) =>  list.dropRight(1).last
        case x@Question(OppgaveTolker("P-03:"), IntList(list)) =>  list(3)
        case x@Question(OppgaveTolker("P-04:"), IntList(list)) =>  list.length
        case x@Question(OppgaveTolker("P-05:"), IntList(list)) =>  list.reverse
        case x@Question(OppgaveTolker("P-07:"), IntListList(list)) =>   list.flatMap(r => r)
        case x@Question(OppgaveTolker("P-08:"), SymbolList(list)) => fjernLikeRepiterendeElementer(list)
        case x@Question(OppgaveTolker("P-09:"), SymbolList(list)) => leggLikeIListe(list)
      }
  }

  def fjernLikeRepiterendeElementer(list:List[Symbol])={
    list.foldRight(List[Symbol]())((next ,collected)=> {
      if(collected.isEmpty)
         next :: collected
      else{
        if(collected.head.equals(next))
          collected
        else
          next :: collected
      }

    })
  }

  def leggLikeIListe(list: List[Symbol]):List[List[Symbol]]={
    val newList = list.foldRight(List[List[Symbol]]())((next, collected ) =>{
      if(collected.isEmpty)
        List(next) :: collected
      else{
        if(collected.head.head.equals(next))
            (next :: collected.head) :: collected.tail
        else
          List(next) :: collected
      }

    })
    println(newList)
    newList
  }

}

object Client extends Application
{
  val client = new Client
  client.run
}


