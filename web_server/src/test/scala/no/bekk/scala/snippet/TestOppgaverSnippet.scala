package no.bekk.scala.snippet

import org.scalatest.{FlatSpec, BeforeAndAfterEach}
import org.scalatest.matchers.ShouldMatchers
import scala.xml.NodeSeq

import no.bekk.scala._

class TestOppgaverSnippet  extends FlatSpec with ShouldMatchers with BeforeAndAfterEach {
  "Oppgaver list" should "show all challenges" in {

    val xml =
    <div class="oppgave">
        <h4><op:rowHeader/></h4>
    </div>;

    val snippet = new Oppgaver with TestChallenges

    val expected =
    <div class="oppgave">
        <h4>Header</h4>
    </div>
    snippet.list(xml).toString should equal(expected.toString)

  }
}

trait TestChallenges
{ self: Oppgaver =>
  override val challengesList = List(new Challenge("Header", "", "svar"))
}