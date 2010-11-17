package no.bekk.scala.snippet

import scala.xml.{NodeSeq,Text}
import net.liftweb.util.BindHelpers._
import net.liftweb.http.SHtml._
import se.scalablesolutions.akka.remote.RemoteServer

class Adress
{
  def index(seq : NodeSeq) ={
    val adress = QuizServer.address match{
      case null => "address"
      case s: String => s
    }
    val port = QuizServer.port match {
      case null  => "port"
      case s:String => s
    }
    bind("adr", seq, "host" -> adress, "port" -> port)
  }
}

object QuizServer
{
  var address:String = null;
  var port:String = null;
}