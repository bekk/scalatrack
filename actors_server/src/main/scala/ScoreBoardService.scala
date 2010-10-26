package no.bekk.scala

trait ScoreBoardService
{
  def chalangeCompleted(team:Team, chalange: Challenge)
}

trait PrintlineScoreBoardService
{
  val scoreBoard = new PrintlineScoreBoard
}

class PrintlineScoreBoard extends ScoreBoardService
{
  def chalangeCompleted(team: Team, chalange: Challenge) = println("completed by " + team + ", " + chalange)
}