package io.github.tools.dataframe.utils

import scala.util.Random

object TestMatch {

  trait ReaderName[T]

  case class ReaderOne(name: String) extends ReaderName[String]

  case class ReaderTwo(name: Int) extends ReaderName[Int]

  // these would be defined as constants on an object somewhere
  val rdo: ReaderName[String] = ReaderOne("one")
  val rdt: ReaderName[Int] = ReaderTwo(2)

  /**
    * value match
    */
  def _match():Unit = {
    val x: Int = Random.nextInt(10)

    x match {
      case 0 => "zero"
      case 1 => "one"
      case 2 => "two"
      case _ => "other"
    }
  }
  
  /**
    * case class match
    * @param readerName
    * @tparam T
    */
  def match0[T](readerName: ReaderName[T]): Unit = readerName match {
    case rdo => println("")
    case rdt => println("")
    case _ => throw new Exception("123")
  }


  /**
    * case class match
    * @param readerName
    */
  def match1(readerName: ReaderName[_]): Unit = readerName match {
    case ReaderOne(name) => println(name)
    case ReaderTwo(name) => println(name)
    case _ => throw new Exception("123")
  }

  /**
    * object type match
    * @param readerName
    */
  def match2(readerName: ReaderName[_]): Unit = readerName match {
    case p: ReaderOne => println(p.name)
    case i: ReaderTwo => println(i.name)
    case _ => throw new Exception("123")
  }

  def main(args: Array[String]): Unit = {
    println("-------------")
  }
}
