package no.bekk.scala

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