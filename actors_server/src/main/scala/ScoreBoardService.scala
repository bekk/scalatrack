package no.bekk.scala

import _root_.no.bekk.scala.{Chalange, Team}

trait ScoreBoardService
{
  def chalangeCompleted(team:Team, chalange: Chalange)
}

trait PrintlineScoreBoardService
{
  val scoreBoard = new PrintlineScoreBoard
}

class PrintlineScoreBoard extends ScoreBoardService
{
  def chalangeCompleted(team: Team, chalange: Chalange) = println("completed by " + team + ", " + chalange)
}