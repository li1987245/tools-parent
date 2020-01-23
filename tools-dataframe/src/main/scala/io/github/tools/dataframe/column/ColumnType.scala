package io.github.tools.dataframe.column

object ColumnType extends Enumeration {

  type ColumnType = Value

  val INTEGER = Value(1, "Integer")
  val LONG = Value(2, "Long")
  val FLOAT = Value(3, "Float")
  val DOUBLE = Value(4, "Double")
  val DATE = Value(8, "Date")
  val BOOLEAN = Value(9, "Boolean")
  val STRING = Value(10, "String")

  //  protected case class Val(mass: Double, radius: Double) extends super.Val {
  //    def surfaceGravity: Double =mass / (radius * radius)
  //    def surfaceWeight(otherMass: Double): Double = otherMass * surfaceGravity
  //  }
  //  import scala.language.implicitConversions
  //  implicit def valueToPlanetVal(x: Value): Val = x.asInstanceOf[Val]

  // Main method
  def main(args: Array[String]) {
    println(s"type = ${ColumnType.values}")
  }
}
