package no.bekk.scala
import scala.util.Random
import no.bekk.scala.messages.Question

trait Challenges
{
  def listOfNumbers() = Range(1,8).map(Random.nextInt).toList
  val symbols:List[Symbol] = List('a,'b,'c,'d,'e)
  def listOfSymbols():List[Symbol] = Range(1,15).toList.map((i:Int)=> symbols(Random.nextInt(symbols.length)))
  val challenges = List(
    new Challenge("Ping", "Svar med pong",   "", (q:Question, tekst:Any) => tekst.equals("pong")),
    new Challenge("Talet etter 1 - 2- 3 - x", "Svar med tallet etter 3",  3, (q:Question, number:Any)=> number == 4),
    new Challenge("P-01: Siste element i listen", "", listOfNumbers() , (q:Question, answer:Any) => q.content.asInstanceOf[List[Int]].last.equals(answer)),
    new Challenge("P-02: Nest siste element i listen", "" , listOfNumbers(), (q:Question, answer:Any) => q.content.asInstanceOf[List[Int]].dropRight(1).last.equals(answer)),
    new Challenge("P-03: Finn det 3 elementet i listen, det første elementet er det 0'te", "",  listOfNumbers(),(q:Question, answer:Any) => q.content.asInstanceOf[List[Int]](3).equals(answer)),
    new Challenge("P-04: Tell antall elemnter i listen", "" , listOfNumbers(),(q:Question, answer:Any)=> answer.equals(q.content.asInstanceOf[List[Int]].length)),
    new Challenge("P-05: Reverser listen", "",  listOfNumbers(), (q:Question, answer:Any) => answer.equals(q.content.asInstanceOf[List[Int]].reverse)),
    new Challenge("P-07: Flat ut listene til en liste", "",  List(List(1, 1), List(2), List(3)), (q:Question, answer:Any) => answer.equals(List(1, 1, 2, 3))),
    new Challenge("P-08: Fjern like elementer, hvis de kommer etterhveranre", "" , listOfSymbols(), (q:Question, answer:Any) => answer.equals(fjernLikeRepiterendeElementer(q.content.asInstanceOf[List[Symbol]]))),
    new Challenge("P-09: Legg like elementer som kommer etter hverandre i hver sin liste", "",  listOfSymbols(), (q:Question, answer:Any) => answer.equals(leggLikeIListe(q.content.asInstanceOf[List[Symbol]])))
  )

  def fjernLikeRepiterendeElementer(list:List[Symbol])={
    list.foldRight(List[Symbol]())((next ,collected)=> {
      if(collected.isEmpty)
         next :: collected
      else{
        if(collected.head.equals(next))
          collected
        else
          next :: collected
      }

    })
  }

  def leggLikeIListe(list: List[Symbol]):List[List[Symbol]]={
    val newList = list.foldRight(List[List[Symbol]]())((next, collected ) =>{
      if(collected.isEmpty)
        List(next) :: collected
      else{
        if(collected.head.head.equals(next))
            (next :: collected.head) :: collected.tail
        else
          List(next) :: collected
      }

    })
    println(newList)
    newList
  }
}

case class Challenge(val question:String, val description:String, val content:Any, val answer:(Question, Any)=> Boolean)