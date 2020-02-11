package io.github.tools.dataframe.function

import io.github.tools.dataframe.column.{Column, NumericColumn}

abstract class NumericAggregate(val name: String) extends Aggregate[Double] {
  self =>
  def summarize(numeric: Array[Double]): Double

  override def apply(column: NumericColumn[_ <: AnyVal]): Double = {
    lazy val numeric: Array[Double] = removeMissing(column)
    summarize(numeric)
  }

  private def removeMissing(column: NumericColumn[_ <: AnyVal]): Array[Double] = {
    column.removeMissing.asInstanceOf[NumericColumn[_ <: AnyVal]].asDoubleArray
  }
}
