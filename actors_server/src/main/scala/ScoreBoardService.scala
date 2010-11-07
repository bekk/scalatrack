package no.bekk.scala

import _root_.no.bekk.scala.messages.Question

trait ScoreBoardService
{
  def chalangeCompleted(team:Team, question: Question)
}

trait PrintlineScoreBoardProvider
{
  val scoreBoard = new PrintlineScoreBoardService
}

class PrintlineScoreBoardService extends ScoreBoardService
{
  def chalangeCompleted(team: Team, question: Question) = println("completed by " + team + ", " + question)
}