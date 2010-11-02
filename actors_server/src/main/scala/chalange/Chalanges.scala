package no.bekk.scala

trait Challenges
{
  val listOfNumbers = List(1, 1, 2, 3, 5, 8)
  val challenges = List(
    new Challenge("Ping",   "", "pong"),
    new Challenge("Talet etter 123",  "", 4),
    new Challenge("P-01: Siste element i listen", listOfNumbers , 8),
    new Challenge("P-02: Nest siste element i listen", List(1, 1, 2, 3, 5, 8), 8),
    new Challenge("P-03: Finn det 3 elementet i listen, det første elementet er det 0'te", listOfNumbers, 2),
    new Challenge("P-04: Tell antall elemnter i listen", listOfNumbers, 6),
    new Challenge("P-05: Reverser listen", listOfNumbers, listOfNumbers.reverse),
    new Challenge("P-07: Flat ut listene til en liste", List(List(1, 1), 2, List(3, List(5, 8))), List(1, 1, 2, 3, 5, 8)),
    new Challenge("P-08: Fjern like elementer, hvis de kommer etterhveranre", List('a, 'a, 'a, 'a, 'b, 'c, 'c, 'a, 'a, 'd, 'e, 'e, 'e, 'e), List('a, 'b, 'c, 'a, 'd, 'e)),
    new Challenge("P-09: Legg like elementer som kommer etter hverandre i hver sin liste", List('a, 'a, 'a, 'a, 'b, 'c, 'c, 'a, 'a, 'd, 'e, 'e, 'e, 'e), List(List('a, 'a, 'a, 'a), List('b), List('c, 'c), List('a, 'a), List('d), List('e, 'e, 'e, 'e)))
  )
}

case class Challenge(val question:String, val content:Any, val answer:Any)