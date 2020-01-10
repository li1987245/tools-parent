package io.github.tools.dataframe.column

import java.util.Comparator
import java.lang.Iterable

import io.github.tools.dataframe.column.ColumnType._

trait Column[T] extends Iterable[T] with Comparator[T] {

  val size: Int

  val asObjectArray: Array[T]

  /**
    * Returns the count of missing values in this column.
    *
    * @return missing values as int
    */
  val countMissing: Int

  /**
    * Returns the count of unique values in this column.
    *
    * @return unique values as int
    */
  val countUnique: Int = unique().size

  /**
    * Returns the column's name.
    *
    * @return name as String
    */
  val name: String

  /**
    * Returns this column's ColumnType
    *
    * @return { @link ColumnType}
    */
  val columnType: ColumnType

  /**
    * Returns a string representation of the value at the given row.
    *
    * @param row The index of the row.
    * @return value as String
    */
  def getString(row: Int): String

  def get(row: Int): T

  def unique(): Column[T]
}
