package io.github.tools.dataframe.function

/**
  * 根据分组列信息生成分组key值
  * 默认为row -> [row(idx0),row(idx2)]
  * @tparam I
  */
trait KeyFunction[I,O] extends Function[Seq[I], O] {

}
