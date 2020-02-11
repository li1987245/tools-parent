package io.github.tools.dataframe.column

import io.github.tools.dataframe.column.ColumnType.ColumnType

import scala.collection.mutable.ArrayBuffer

class DoubleColumn(val name: AnyRef) extends NumericColumn[Double] {

  private val MISSING_VALUE: Double = Double.MinValue
  val dtype: ColumnType = ColumnType.DOUBLE
  val data: ArrayBuffer[Double] = new ArrayBuffer[Double]()

  override def getDouble(index: Int): Double = get(index)

  /**
    * 生成对应空容器集合
    *
    * @return
    */
  override def emptyCopy: Column[Double] = new DoubleColumn(name)

  /**
    * 复制容器集合
    *
    * @return
    */
  override def copy: Column[Double] = {
    val tmp = new DoubleColumn(name)
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
  override def copyEmpty: Column[Double] = {
    new DoubleColumn(name)
  }

  override def removeMissing: Column[Double] = {
    val iter = data.iterator
    val tmp = new DoubleColumn(name)
    for (e <- iter.dropWhile(isMissingValue))
      tmp.append(e)
    tmp
  }

  override def toArray: Array[Double] = data.toArray

  override def get(row: Int): Double = data(row)

  override def getString(row: Int): String = Option(get(row)).getOrElse("-").toString

  override def set(row: Int, value: Double): Unit = data.insert(row, value)

  override def setObj(row: Int, value: Any): Unit = {
    val _value: Double = toDouble(value)
    set(row, _value)
  }

  override def append(value: Double*): Unit = data.append(value: _*)

  override def addObj(value: Any): Unit = {
    val _value: Double = toDouble(value)
    append(_value)
  }

  override def unique(): Set[Double] = data.toSet

  override def countMissing: Int = data.count(isMissingValue)

  /**
    * 当value为空或者为MinValue返回true
    *
    * @param value
    * @return
    */
  def isMissingValue(value: Double): Boolean = Option(value) match {
    case Some(v) => v == MISSING_VALUE
    case _ => true
  }

  override def iterator: Iterator[Double] = data.iterator

  override def size: Int = data.size

  private def toDouble(value: Any) = {
    val _value: Double = Option(value) match {
      case Some(v) =>
        v match {
          case i: Double => i.asInstanceOf[Double]
          case s: String => s.toString.toDouble
        }
      case _ => MISSING_VALUE
    }
    _value
  }

  override def missingValue: Double = MISSING_VALUE
}


object DoubleColumn {
  /**
    * 等同于DoubleColumn.apply
    *
    * @return
    */
  def create(): DoubleColumn = {
    new DoubleColumn(null)
  }
}
