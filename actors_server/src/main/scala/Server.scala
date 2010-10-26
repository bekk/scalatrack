package no.bekk.scala

import _root_.no.bekk.scala._
import _root_.no.bekk.scala.messages._
import se.scalablesolutions.akka.actor._
import se.scalablesolutions.akka.remote._

abstract class Server extends Actor{
  val challenges: List[Challenge];
  var teamChallenges = Map[Team, Int]();

  val scoreBoard : ScoreBoardService;
  
  private def nextChallenge(team:Team) = {
    teamChallenges.get(team) match {
      case None => {
        val next = challenges(0)
        teamChallenges = teamChallenges + ((team, 1))
        next
      }
      case Some(challenge) => {
        val next = challenges(challenge)
        if(challenge +1 < challenges.length)
          teamChallenges = teamChallenges + ((team, challenge +1))
        else
          teamChallenges = teamChallenges + ((team, 0))
        
        next
      }
    }

  }

  def handleAnswer(team: Team, chalange:Question, answer:String)={
    val answeredChallenge = new Challenge(chalange.question, answer)
    if(challenges.exists(_.equals(answeredChallenge))){
      scoreBoard.chalangeCompleted(team, answeredChallenge)
      Correct()
    } else
      Wrong()
  }

  def receive={
    case MoreChallenges(team) => self.reply(Question(nextChallenge(team).question))
    case Answer(team, challenge, answer) => self.reply(handleAnswer(team, challenge, answer))
  }
}


object Server {
  def main(args : Array[String])= {
    RemoteNode.start("localhost", 9999)
    RemoteNode.register("Server", Actor.actorOf(new Server with Challenges with PrintlineScoreBoardService))
  }
}