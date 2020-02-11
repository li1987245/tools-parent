package io.github.tools.dataframe.function

import io.github.tools.dataframe.column.{Column, NumericColumn}

trait Aggregate[O] extends Function[NumericColumn[_ <: AnyVal], O] {
  val name: String
}
