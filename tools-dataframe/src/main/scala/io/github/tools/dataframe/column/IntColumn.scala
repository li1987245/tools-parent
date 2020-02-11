package io.github.tools.dataframe.column

import io.github.tools.dataframe.column.ColumnType.ColumnType

import scala.collection.mutable.ArrayBuffer

class IntColumn(val name: AnyRef) extends NumericColumn[Int] {

  private val MISSING_VALUE: Int = Int.MinValue
  val dtype: ColumnType = ColumnType.INTEGER
  val data: ArrayBuffer[Int] = new ArrayBuffer[Int]()

  override def getDouble(index: Int): Double = get(index).toDouble

  /**
    * 生成对应空容器集合
    *
    * @return
    */
  override def emptyCopy: Column[Int] = new IntColumn(name)

  /**
    * 复制容器集合
    *
    * @return
    */
  override def copy: Column[Int] = {
    val tmp = new IntColumn(name)
    for (e <- data) {
      tmp.append(e)
    }
    tmp
  }

  /**
    * 复制容器集合
    *
    * @return
    */
  override def copyEmpty: Column[Int] = {
    new IntColumn(name)
  }

  override def removeMissing: Column[Int] = {
    val iter = data.iterator
    val tmp = new IntColumn(name)
    for (e <- iter.dropWhile(isMissingValue))
      tmp.append(e)
    tmp
  }

  override def toArray: Array[Int] = data.toArray

  override def get(row: Int): Int = data(row)

  override def getString(row: Int): String = Option(get(row)).getOrElse("-").toString

  override def set(row: Int, value: Int): Unit = data.insert(row, value)

  override def setObj(row: Int, value: Any): Unit = {
    val _value: Int = toInt(value)
    set(row, _value)
  }

  override def append(value: Int*): Unit = data.append(value: _*)

  override def addObj(value: Any): Unit = {
    val _value: Int = toInt(value)
    append(_value)
  }

  override def unique(): Set[Int] = data.toSet

  override def countMissing: Int = data.count(isMissingValue)

  /**
    * 当value为空或者为MinValue返回true
    *
    * @param value
    * @return
    */
  def isMissingValue(value: Int): Boolean = Option(value) match {
    case Some(v) => v == MISSING_VALUE
    case _ => true
  }

  override def iterator: Iterator[Int] = data.iterator

  override def size: Int = data.size

  private def toInt(value: Any) = {
    val _value: Int = Option(value) match {
      case Some(v) =>
        v match {
          case i:Int => i.asInstanceOf[Int]
          case s:String => s.toString.toInt
        }
      case _ => MISSING_VALUE
    }
    _value
  }

  override def missingValue: Int = MISSING_VALUE
}


object IntColumn {
  /**
    * 等同于IntColumn.apply
    *
    * @return
    */
  def create(): IntColumn = {
    new IntColumn(null)
  }
}
