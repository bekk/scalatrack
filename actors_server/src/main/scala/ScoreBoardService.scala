package no.bekk.scala

import _root_.no.bekk.scala.messages.Question
import _root_.no.bekk.scala.Team

trait ScoreBoardService
{
  def challengeCompleted(team:Team, question: Question)
  def challengeFailed(team:Team, question: Question)
}

trait PrintlineScoreBoardProvider
{
  val scoreBoard = new PrintlineScoreBoardService
}

class PrintlineScoreBoardService extends ScoreBoardService
{
  def challengeCompleted(team: Team, question: Question) = println("completed by " + team + ", " + question)
  def challengeFailed(team: Team, question: Question) = println("faild " + question + ", " + team )
}