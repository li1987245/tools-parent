package io.github.tools.dataframe.column


import io.github.tools.dataframe.column.ColumnType._
import io.github.tools.dataframe.selection.BitSet

import scala.beans.BeanProperty

trait Column[T] extends Iterable[T] {

  val name: AnyRef
  val dtype: ColumnType

  def toArray: Array[T]

  def get(row: Int): T

  def getString(row: Int): String

  def set(row: Int, value: T)

  def setObj(row: Int, value: Any)

  def append(value: T*)

  def addObj(value: Any)

  def size: Int

  def unique(): Set[T]

  def missingValue: T

  def countMissing: Int

  def countUnique: Int = unique().size

  /**
    * 复制容器集合
    *
    * @return
    */
  def copy: Column[T]

  def copyEmpty: Column[T]

  def slice(rows: BitSet): Column[T] = {
    val column = this.copyEmpty
    for (row <- rows) {
      column.append(this.get(row))
    }
    column
  }
}
