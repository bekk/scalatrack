package no.bekk.scala.snippet

import _root_.no.bekk.scala.lib.ChallengesList
import scala.xml.{NodeSeq,Text}
import no.bekk.scala.Challenge
import net.liftweb.util.BindHelpers._
import net.liftweb.http.SHtml._


class Oppgaver
{
  val challengesList = ChallengesList()

  def list(seq: NodeSeq):NodeSeq={
    challengesList.flatMap( challenge =>
       bind("op", seq, "rowHeader" -> challenge.question,
         "rowDescription" -> challenge.description, 
         "rowExample" -> (challenge.content + ""))
    )
  }
}
