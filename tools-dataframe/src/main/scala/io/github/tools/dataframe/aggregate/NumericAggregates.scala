package io.github.tools.dataframe.aggregate

import io.github.tools.dataframe.column.Column
import io.github.tools.dataframe.function.NumericAggregate
import org.apache.commons.math3.stat.StatUtils

object NumericAggregates {
  lazy val mean: NumericAggregate = new NumericAggregate("mean") {
    override def summarize(numeric: Array[Double]): Double = StatUtils.mean(numeric)
  }

}
