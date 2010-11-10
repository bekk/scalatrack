package no.bekk.scala.lib

import _root_.no.bekk.scala.comet.LeretServer
import no.bekk.scala._
import no.bekk.scala.messages._


trait CometServerScoreboardProvider
{
  val scoreBoard = new CometServerScoreboardService
}

class CometServerScoreboardService extends ScoreBoardService
{
  def challengeCompleted(team: Team, quiestion: Question) = {
    TeamRegisterWithChallenges.registerCompletedChalange(team, new Question(quiestion.question, quiestion.content), Some(quiestion))
    LeretServer ! new ChangesToTheScoreboard()
  }

  def challengeFailed(team : Team, question:Question) = {
    TeamRegisterWithChallenges.registerFaildChallenge(team, question)
    LeretServer ! new ChangesToTheScoreboard()
  }

}