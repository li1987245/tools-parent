package io.github.tools.dataframe.column

import io.github.tools.dataframe.column.ColumnType.ColumnType
import io.github.tools.dataframe.function.Predicate

abstract class AbstractColumn[T] extends Column[T] {

  protected final val DEFAULT_ARRAY_SIZE:Int = 128

  def filter(test: Predicate[_ >: T]): Column[T] = {
    val result: Column[T] = emptyCopy
    for (t <- this) {
      if (test(t)) result.append(t)
    }
    result
  }

  def subset(rows: Int*): Column[T] = {
    val result = this.emptyCopy
    for (row <- rows) {
      result.append(get(row))
    }
    result
  }

  /**
    * 生成对应空容器集合
    *
    * @return
    */
  def emptyCopy: Column[T]

  def removeMissing: Column[T]
}
