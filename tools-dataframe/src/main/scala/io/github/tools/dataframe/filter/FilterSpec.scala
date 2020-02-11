package io.github.tools.dataframe.filter

import io.github.tools.dataframe.function.Predicate
import io.github.tools.dataframe.selection.BitSet

trait FilterSpec[T] {

  def isNotMissing: Predicate[T]

  def isMissing: Predicate[T]
}