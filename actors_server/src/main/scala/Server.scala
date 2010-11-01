package no.bekk.scala

import _root_.no.bekk.scala._
import _root_.no.bekk.scala.messages._
import se.scalablesolutions.akka.actor._
import se.scalablesolutions.akka.remote._

abstract class Server extends Actor{
  val challenges: List[Challenge];
  val scoreBoard : ScoreBoardService;

  var teamChallenges = Map[Team, Int]();

  private def nextChallenge(team:Team) : Challenge = {
    def findFirstChallenge: Challenge = {
      val next = challenges(0)
      teamChallenges = teamChallenges + ((team, 1))
      next
    }

    def findNextChallenge(challenge: Int): Challenge = {
      val next = challenges(challenge)
      setNextChallenge(challenge)

      next
    }

    def setNextChallenge(challenge:Int) {
      if (challenge + 1 < challenges.length)
        teamChallenges = teamChallenges + ((team, challenge + 1))
      else
        teamChallenges = teamChallenges + ((team, 0))
    }

    teamChallenges.get(team) match {
      case None => {
        findFirstChallenge
      }
      case Some(challenge) => {
        findNextChallenge(challenge)
      }
    }
  }

  private def handleAnswer(team: Team, chalange:Question, answer:Any):Verdict={
    val answeredChallenge = new Challenge(chalange.question, chalange.content, answer)
    if(challenges.exists(_.equals(answeredChallenge))){
      scoreBoard.chalangeCompleted(team, answeredChallenge)
      Correct()
    } else
      Wrong()
  }

  def receive={
    case MoreChallenges(team) => {
      val challenge: Challenge = nextChallenge(team)
      self.reply(Question(challenge.question, challenge.content))
    }
    case Answer(team, challenge, answer) => self.reply(handleAnswer(team, challenge, answer))
  }
}


object Server {
  def main(args : Array[String])= {
    RemoteNode.start("localhost", 9999)
    RemoteNode.register("Server", Actor.actorOf(new Server with Challenges with PrintlineScoreBoardProvider))
  }
}