package io.github.tools.dataframe.filter

import io.github.tools.dataframe.function.Predicate

trait NumberFilterSpec[T] extends FilterSpec[T] {
  def isEqualTo(other: Double): Predicate[T]

  def isBetween(start: Double, end: Double): Predicate[T]

  def isBetweenExclusive(start: Double, end: Double): Predicate[T]

  def isGreaterThan(f: Double): Predicate[T]

  def isGreaterThanOrEqualTo(f: Double): Predicate[T]

  def isLessThan(f: Double): Predicate[T]

  def isLessThanOrEqualTo(f: Double): Predicate[T]

  def isIn(numbers: Number*): Predicate[T]

  def isNotIn(numbers: Number*): Predicate[T]

  def isEqualTo(d: Number): Predicate[T]

  def isNotEqualTo(d: Number): Predicate[T]
}