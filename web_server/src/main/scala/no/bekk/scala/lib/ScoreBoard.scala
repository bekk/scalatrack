package no.bekk.scala.lib

import _root_.no.bekk.scala.comet.LeretServer
import no.bekk.scala._


trait CometServerScoreboardProvider
{
  val scoreBoard = new CometServerScoreboardService
}

class CometServerScoreboardService extends ScoreBoardService
{
  def chalangeCompleted(team: Team, challenge: Challenge) = {
    LeretServer ! new CompletedChallenge(team, challenge)
  }
}