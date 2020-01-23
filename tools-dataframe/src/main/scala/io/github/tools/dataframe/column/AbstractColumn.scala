package io.github.tools.dataframe.column

import scala.collection.JavaConverters._
import io.github.tools.dataframe.column.ColumnType.ColumnType
import io.github.tools.dataframe.function.Predicate

abstract class AbstractColumn[T] extends Column[T] {

  protected final val DEFAULT_ARRAY_SIZE:Int = 128

  def this(name: String, columnType: ColumnType) {
    this()
    this.name = name
    this.columnType = columnType
  }

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

  /**
    * 复制容器集合
    *
    * @return
    */
  def copy: Column[T]

  def removeMissing: Column[T]
}
