package io.github.tools.dataframe.function

import org.apache.commons.math3.stat.descriptive.StorelessUnivariateStatistic


abstract class AbstractCumulativeAggregate(stat: StorelessUnivariateStatistic, initialValue: Number) extends CumulativeAggregate {

  override def apply(value: Number): Number = {
    stat.increment(value.doubleValue)
    stat.getResult
  }

  def reset(): Unit = {
    stat.clear()
    stat.increment(initialValue.doubleValue)
  }
}