package io.github.tools.dataframe.function

trait Function[I,O] {
  /**
    *
    * @param value
    * @return
    */
  def apply(value: I): O
}
