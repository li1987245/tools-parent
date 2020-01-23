package io.github.tools.dataframe.function

import io.github.tools.dataframe.column.{Column, NumericColumn}

trait Aggregate[O] extends Function[NumericColumn[_ <: Number], O] {
  val name: String
}
