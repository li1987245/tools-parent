package io.github.tools.dataframe.internal

/**
  * 泛型
  *
  * @param values
  */
class GenericRow(values: Seq[Any], schema: Index[AnyRef]) extends Row {

  def this(values: Seq[Any]) {
    this(values, null)
  }

  protected def this() = this(null)

  def this(size: Int) = this(new Array[Any](size))

  override def length: Int = values.length

  override def get(i: Int): Any = values(i)

  /**
    * 根据列名查找对应列信息
    * @param name
    * @tparam T
    * @return
    */
  override def get[T](name: AnyRef): T = {
    Option(schema) match {
      case Some(idx) => getAs(idx.get(name))
      case _ => throw new IllegalArgumentException(s"can`t find $name,schema is null")
    }

  }

  override def toSeq: Seq[Any] = values

  override def copy(): GenericRow = this
}

object GenericRow{
  def of(value: Seq[Any]): Row = {
    new GenericRow(value)
  }
}