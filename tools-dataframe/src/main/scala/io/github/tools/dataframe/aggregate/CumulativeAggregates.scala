package io.github.tools.dataframe.aggregate

import io.github.tools.dataframe.function.AbstractCumulativeAggregate
import org.apache.commons.math3.stat.descriptive.summary.{Sum,Product}

/**
  * 累计计算
  */
object CumulativeAggregates {

  lazy val cumulativeSum = new AbstractCumulativeAggregate(new Sum, 0) {}

  /**
    * 乘积
    */
  lazy val cumulativeProduct = new AbstractCumulativeAggregate(new Product, 1) {}

}
