package io.github.tools.dataframe.utils

import java.lang.reflect.ParameterizedType

import io.github.tools.dataframe.column.IntColumn

import scala.collection.mutable.ListBuffer

trait Column[T] {
  def set(row: Int, value: T)

  def setObj(row: Int, value: Any)
}

class IntColumn extends Column[Int] {
  val list: List[Int] = List[Int]()

  override def set(row: Int, value: Int): Unit = println(value)

  override def setObj(row: Int, value: Any): Unit = {
    value match {
      case i: Int => set(row, value.asInstanceOf[Int])
      case s: String => set(row, value.toString.toInt)
      case _ =>
    }

  }
}

class Animal(style: String) {
  def getStyle(): String = {
    style
  }

}

class Cat(name: String) extends Animal("land") {
  def getName(): String = {
    name
  }
}

class BlackCat(color: String) extends Cat("cat") {
  def getColor(): String = {
    color
  }
}

class Printer[+A] {
  def print[B >: A](value: B): Unit = {
    println(value.getClass.getName)
    println(value)
  }
}

object Test {
  def main(args: Array[String]): Unit = {
    val test = new Test
    test.add(new IntColumn)
    test.set(0, 1, new IntColumn)
    val p: Printer[Cat] = new Printer[Cat]()
    p.print(new Animal(""))
    p.print(new Cat(""))
    p.print("123")
  }
}

class Test {
  private val blocks: ListBuffer[Column[_]] = ListBuffer[Column[_]]()

  val f:Double=>Double = (double:Double)=>{
    double
  }

  def add[T](value: Column[T]): Unit = {
    blocks += value
  }

  def set(col: Int, row: Int, value: Any): Unit = {
    blocks(col).setObj(row, value)
  }
}