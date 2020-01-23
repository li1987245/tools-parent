package io.github.tools.dataframe

import io.github.tools.dataframe.column.Column
import io.github.tools.dataframe.column.ColumnType.ColumnType
import io.github.tools.dataframe.function.Predicate
import io.github.tools.dataframe.index.Index
import io.github.tools.dataframe.internal.BlockManager
import io.github.tools.dataframe.join.JoinType.JoinType
import io.github.tools.dataframe.join.Joiner
import io.github.tools.dataframe.selection.Selection

import scala.collection.immutable.{ListMap, TreeMap}
import scala.collection.mutable


class DataFrame(name: String) extends Iterable[AnyRef] {

  self => //this别名

  private[this] val index: Index[String] = new Index()
  private[this] val columns: Index[AnyVal] = new Index()
  private[this] val data: BlockManager = new BlockManager()

  val ASCENDING: String = "asc"
  val DESCENDING: String = "desc"

  def this() {
    this("default")
  }

  /**
    *
    * @param name
    * @param column
    */
  def this(name: String, column: List[String]) {
    this(name)
    //(element,index)
    column.zipWithIndex.foreach(x => {
      columns.add(x._1, x._2)
    })
  }

  /**
    * 根据元数据信息初始化df
    *
    * @param name
    * @param schema
    */
  def this(name: String, schema: ListMap[String, ColumnType]) {
    this(name)
    schema.zipWithIndex.foreach(x => {
      columns.add(x._1._1, x._2)
      data.add
    })
  }

  /**
    * 根据元数据信息初始化df，并装载数据
    *
    * @param name
    * @param schema
    * @param data
    */
  def this(name: String, schema: Map[String, ColumnType], data: List[List[Any]]) {
    this(name)
    column.zipWithIndex.foreach(x => {
      columns.add(x._1, x._2)
    })
  }

  def addColumns(columns: Column[_]*): Unit = {

  }

  def add(columnName: String, record: Column[_]): Unit = {

  }

  def get(idx: Int): Column[_] = {
    data
  }


  /**
    * 根据bitmap位运算
    *
    * @param predicate
    * @return
    */
  def filter(predicate: Predicate[_]): DataFrame = {
    val df: DataFrame = copyEmpty()

  }

  /**
    * 根据bitmap中的index返回新的DataFrame
    *
    * @param selection
    * @return
    */
  def filter(selection: Selection): DataFrame = {

  }

  /**
    * 使用comparator{使用get(index,columnIdx)得到行列对应元素排序}对index排序，根据排序后index复制生成新的dataframe
    *
    * @param columnNames
    */
  def sortedBy(columnNames: String*)


  def joinOn(columnNames: String*): Joiner


  def copyEmpty(): DataFrame = {
    new DataFrame()
  }


  private def copyToDest(selection: Selection, origin: DataFrame, dest: DataFrame) = {
    for (column <- columns) {

    }
  }

  override def iterator: Iterator[List[AnyVal]] = iterrows

  /**
    * 按行遍历，通过Views实现转置
    * @return
    */
  def iterrows: Iterator[List[AnyVal]] = new Views.ListView[V](this, true).listIterator

  case class Cell(t: Any, cellType: ColumnType)

}

object DataFrame {

}
