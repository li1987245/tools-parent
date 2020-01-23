package io.github.tools.dataframe.filter

import io.github.tools.dataframe.function.Predicate
import io.github.tools.dataframe.selection.Selection

/**
  * todo 惰性执行
  * @tparam T
  */
trait Filter[T] {
  /**
    * 根据predicate表达式生成selection，获得行索引
    * @param predicate
    * @return
    */
  def eval(predicate: Predicate[T]): Selection
}
