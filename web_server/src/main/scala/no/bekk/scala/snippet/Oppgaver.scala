package no.bekk.scala.snippet

import _root_.no.bekk.scala.lib.ChallengesList
import scala.xml.NodeSeq
import no.bekk.scala.Challenges
import net.liftweb.util.BindHelpers._


class Oppgaver
{
  val challengesList = ChallengesList()

  def list(seq: NodeSeq):NodeSeq={
    challengesList.flatMap( challenge =>
       bind("op", seq,
         "rowHeader" -> challenge.question ,
         "rowDescriptoin" -> challenge.description ,
         "rowExample" -> challenge.content )
    )
  }
}
