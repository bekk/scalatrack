package no.bekk.scala

trait ScoreBoardService
{
  def chalangeCompleted(team:Team, chalange: Challenge)
}

trait PrintlineScoreBoardProvider
{
  val scoreBoard = new PrintlineScoreBoardService
}

class PrintlineScoreBoardService extends ScoreBoardService
{
  def chalangeCompleted(team: Team, chalange: Challenge) = println("completed by " + team + ", " + chalange)
}