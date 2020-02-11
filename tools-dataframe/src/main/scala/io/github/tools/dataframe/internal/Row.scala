package io.github.tools.dataframe.internal

trait Row {
  def size: Int = length

  def length: Int

  def apply(i: Int): Any = get(i)

  def get(i: Int): Any

  def get[T](name: AnyRef): T

  def isNullAt(i: Int): Boolean = get(i) == null

  def getBoolean(i: Int): Boolean = getAnyValAs[Boolean](i)

  def getByte(i: Int): Byte = getAnyValAs[Byte](i)

  def getShort(i: Int): Short = getAnyValAs[Short](i)

  def getInt(i: Int): Int = getAnyValAs[Int](i)

  def getLong(i: Int): Long = getAnyValAs[Long](i)

  def getFloat(i: Int): Float = getAnyValAs[Float](i)

  def getDouble(i: Int): Double = getAnyValAs[Double](i)

  def getString(i: Int): String = getAs[String](i)

  def toSeq: Seq[Any]

  def copy(): Row

  def getAs[T](i: Int): T = get(i).asInstanceOf[T]

  private def getAnyValAs[T <: AnyVal](i: Int): T =
    if (isNullAt(i)) throw new NullPointerException(s"Value at index $i is null")
    else getAs[T](i)

}

