package io.github.tools.dataframe.aggregate

import io.github.tools.dataframe.function.NumericAggregate
import org.apache.commons.math3.stat.StatUtils

object NumericAggregates {

  val sum: NumericAggregate = new NumericAggregate("sum") {
    override def summarize(numeric: Array[Double]): Double = StatUtils.sum(numeric)
  }

  val mean, avg: NumericAggregate = new NumericAggregate("mean") {
    override def summarize(numeric: Array[Double]): Double = StatUtils.mean(numeric)
  }

  val max: NumericAggregate = new NumericAggregate("max") {
    override def summarize(numeric: Array[Double]): Double = StatUtils.max(numeric)
  }

  val min: NumericAggregate = new NumericAggregate("min") {
    override def summarize(numeric: Array[Double]): Double = StatUtils.min(numeric)
  }

  /**
    * 百分比
    */
  val percentile: Double => NumericAggregate = (pct: Double) => {
    new NumericAggregate(s"percentile$pct") {
      override def summarize(numeric: Array[Double]): Double = StatUtils.percentile(numeric, pct)
    }
  }

  val percentile75: NumericAggregate = percentile(75)

  val percentile90: NumericAggregate = percentile(90)

}
