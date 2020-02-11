package io.github.tools.dataframe.internal

import com.typesafe.scalalogging.Logger
import io.github.tools.dataframe.column.Column

import scala.collection.mutable.ListBuffer

/**
  * todo df通过索引还是列名关联block
  */
class BlockManager {
  private[this] val logger = Logger(this.getClass)
  //按列存储数据，通过索引定位
  private val blocks: ListBuffer[Column[_]] = ListBuffer[Column[_]]()

  def addColumn(column: Column[_]): Unit = {
    blocks += column
  }

  def getColumn(idx: Int): Column[_] = {
    if (idx >= length) {
      logger.error(s"index $idx out of range")
      throw new IndexOutOfBoundsException
    }
    blocks(idx)
  }

  def set[T](col: Int, row: Int, value: T): Unit = {
    blocks(col).setObj(row, value)
  }

  def append(col: Int, value: Any): Unit = {
    blocks(col).addObj(value)
  }

  def length: Int = {
    if (blocks.isEmpty)
      0
    else
      blocks.head.size

  }
}
