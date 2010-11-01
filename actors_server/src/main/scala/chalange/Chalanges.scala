package no.bekk.scala

trait Challenges
{
  val challenges = List(
    new Challenge("tester",   "", "svar"),
    new Challenge("tester2",  "", "svar"),
    new Challenge("tester3",  "", "svar")
  )
}

case class Challenge(val question:String, val content:Any, val answer:Any)