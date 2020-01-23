package io.github.tools.dataframe.column


import io.github.tools.dataframe.column.ColumnType._

import scala.beans.BeanProperty

trait Column[T] extends Iterable[T] {

  @BeanProperty var size: Int

  @BeanProperty var name: String
  @BeanProperty var columnType: ColumnType

  def toArray: Array[T]

  /**
    * Returns the count of missing values in this column.
    *
    * @return missing values as int
    */
  def countMissing: Int

  /**
    * Returns the count of unique values in this column.
    *
    * @return unique values as int
    */
  def countUnique: Int = unique().size

  /**
    * Returns a string representation of the value at the given row.
    *
    * @param row The index of the row.
    * @return value as String
    */
  def getString(row: Int): String

  def append(element: T)

  def get(row: Int): T

  def unique(): Column[T]
}
