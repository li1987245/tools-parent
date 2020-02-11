package io.github.tools.dataframe.function

class DefaultKeyFunction extends KeyFunction[Any, Seq[Any]] {
  /**
    *
    * @param value
    * @return
    */
  override def apply(value: Seq[Any]): Seq[Any] = {
    value
  }
}
