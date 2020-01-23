package io.github.tools.dataframe.filter

import io.github.tools.dataframe.function.Predicate
import io.github.tools.dataframe.selection.{BitmapBackedSelection, Selection}

object DeferredNumberFilterSpec extends NumberFilterSpec[Double] {

  /**
    * 大于
    *
    * @param other
    * @return
    */
  def isGreaterThan(other: Double): Predicate[Double] = (value: Double) => value > other

  /**
    * 大于等于
    *
    * @param other
    * @return
    */
  def isGreaterThanOrEqualTo(other: Double): Predicate[Double] = (value: Double) => value >= other

  override def isEqualTo(other: Double): Predicate[Double] = (value: Double) => value == other

  override def isBetween(start: Double, end: Double): Predicate[Double] = (value: Double) => value >= start && value <= end

  override def isBetweenExclusive(start: Double, end: Double): Predicate[Double] = (value: Double) => value >= start && value <= end

  override def isLessThan(other: Double): Predicate[Double] = (value: Double) => value == other

  override def isLessThanOrEqualTo(other: Double): Predicate[Double] = (value: Double) => value == other

  override def isIn(numbers: Number*): Predicate[Double] = (value: Double) => {
    for (number <- numbers) {
      if (value == number.doubleValue())
        true
    }
    false
  }

  override def isNotIn(numbers: Number*): Predicate[Double] = (value: Double) => {
    for (number <- numbers) {
      if (value == number.doubleValue())
        false
    }
    true
  }

  override def isEqualTo(other: Number): Predicate[Double] = (value: Double) => value == other.doubleValue()

  override def isNotEqualTo(other: Number): Predicate[Double] = (value: Double) => value != other.doubleValue()

  override def isNotMissing: Predicate[Double] = (value: Double) => value != Double.NaN

  override def isMissing: Predicate[Double] = (value: Double) => value == Double.NaN
}
