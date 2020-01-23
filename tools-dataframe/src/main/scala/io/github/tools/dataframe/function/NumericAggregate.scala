package io.github.tools.dataframe.function

import io.github.tools.dataframe.column.{Column, NumericColumn}

abstract class NumericAggregate(val name: String) extends Aggregate[Double] {
  self =>
  def summarize(numeric: Array[Double]): Double

  override def apply(column: NumericColumn[_ <: Number]): Double = {
    lazy val numeric: Array[Double] = removeMissing(column)
    summarize(numeric)
  }

  private def removeMissing(column: NumericColumn[_ <: Number]): Array[Double] = {
    column.removeMissing.asInstanceOf[NumericColumn[_ <: Number]].asDoubleArray
  }
}
