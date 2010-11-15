package no.bekk.scala.lib

import _root_.no.bekk.scala.comet.LeretServer
import no.bekk.scala.messages._
import no.bekk.scala.ScoreBoardService
import no.bekk.scala.Team


trait CometServerScoreboardProvider
{
  val scoreBoard = new CometServerScoreboardService
}

class CometServerScoreboardService extends ScoreBoardService
{
  def challengeCompleted(team: Team, question: Question) = {
    TeamRegisterWithChallenges.registerCompletedChalange(team, question, Some(question))
    LeretServer ! new ChangesToTheScoreboard()
  }

  def challengeFailed(team : Team, question:Question) = {
    TeamRegisterWithChallenges.registerFaildChallenge(team, question)
    LeretServer ! new ChangesToTheScoreboard()
  }

}