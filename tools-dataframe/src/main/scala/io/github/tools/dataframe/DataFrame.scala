package io.github.tools.dataframe

import com.typesafe.scalalogging.Logger
import io.github.tools.dataframe.aggregate.AggregateDataFrame
import io.github.tools.dataframe.column.ColumnType.ColumnType
import io.github.tools.dataframe.column.{Column, ColumnType, IntColumn}
import io.github.tools.dataframe.function.{Aggregate, NumericAggregate}
import io.github.tools.dataframe.internal.{BlockManager, GenericRow, Index, Row, Views}
import io.github.tools.dataframe.join.Joiner
import io.github.tools.dataframe.selection.BitSet
import io.github.tools.dataframe.transform.Pivoting

import scala.collection.JavaConverters._
import scala.collection.immutable.ListMap
import scala.collection.mutable.ArrayBuffer


/**
  * data.select('columns').distinct()
  *
  * people.filter("age > 30")
  * .join(department, people("deptId") === department("id"))
  * .groupBy(department("name"), people("gender"))
  * .agg((avg,people("salary")), (max,people("age")))
  *
  * @param index   预留
  * @param columns 列信息
  * @param name    df名称
  */
class DataFrame(name: String, index: Index[String] = new Index(), val columns: Index[AnyRef] = new Index()) extends Iterable[List[_]] {

  //this别名
  self =>

  private[this] val logger = Logger(this.getClass)
  private[this] val data: BlockManager = new BlockManager()


  def this() {
    this("default")
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
      //schema ->(column name,index)
      val columName = x._1._1
      val columnType = x._1._2
      val idx = x._2
      columns.add(columName, idx)
      val column: Column[_] = columnType match {
        case ColumnType.INTEGER => new IntColumn(x._1._1)
        case _ =>
          logger.error(s"$columnType not support")
          throw new RuntimeException(s"$columnType not support")
      }
      data.addColumn(column)
    })
  }

  /**
    * 装载数据
    *
    * @param name
    * @param schema
    * @param data
    */
  def this(name: String, schema: ListMap[String, ColumnType], data: List[List[Any]]) {
    this(name, schema)
    for (elements <- data) {
      for (elt <- elements.zipWithIndex) {
        this.getColumn(elt._2).addObj(elt._1)
      }
    }
  }

  def addColumns(columns: Column[_]*): Unit = {
    for (column <- columns) addColumn(column.name, column)
  }

  def addColumn(columnName: AnyRef, record: Column[_]): Unit = {
    if (columnName == null) logger.warn("column name is null")
    columns.add(columnName)
    data.addColumn(record)
  }

  /**
    * 新增行
    *
    * @param rows
    */
  def addRows(rows: Row*): Unit = {
    for (row <- rows) {
      row.toSeq.zipWithIndex.foreach(x => {
        data.getColumn(x._2).addObj(x._1)
      })
    }
  }

  def getColumn(idx: Int): Column[_] = {
    data.getColumn(idx)
  }

  def getColumn(name: AnyRef): Column[_] = {
    data.getColumn(columns.get(name))
  }

  def get(col: Int, row: Int): Any = {
    data.getColumn(col).get(row)
  }

  def getAs[T](col: Int, row: Int): T = {
    data.getColumn(col).get(row).asInstanceOf[T]
  }

  def getRow(row: Int): Row = {
    val tmp = ArrayBuffer[Any]()
    for (idx <- 0 until columns.size()) {
      tmp += data.getColumn(idx).get(row)
    }
    new GenericRow(tmp, columns)
  }

  /**
    * 选择某几列
    *
    * @param colNames
    * @return
    */
  def select(colNames: AnyRef*): DataFrame = {
    val df = new DataFrame(name)
    for (colName <- colNames) {
      df.addColumn(colName, data.getColumn(columns.get(colName)).copy)
    }
    df
  }

  /**
    * 按条件过滤
    * filter(and(df.get('col1').isEqualTo(123)))
    *
    * @param selection
    * @return
    */
  def filter(selection: BitSet): DataFrame = {
    val df = copyEmpty()
    for (value <- this) {
      df.addRows(new GenericRow(value))
    }
    df
  }

  /**
    * 使用comparator{使用get(index,columnIdx)得到行列对应元素排序}对index排序，根据排序后index复制生成新的dataframe
    *
    * @param columnNames
    */
  def sortedBy(columnNames: String*): DataFrame = ???


  def joinOn(columnNames: String*): Joiner = ???

  def pivot(rows: Array[Int], cols: Array[Int], values: Array[Int]): DataFrame = Pivoting.pivot(this, rows, cols, values)

  /**
    * 实现分组小计
    *
    * @param cols
    * @return
    */
  def rollup(cols: Any*): AggregateDataFrame = ???

  def groupBy(cols: Any*): AggregateDataFrame = groupBy(columns.indices(cols))

  def groupBy(cols: Array[Int]): AggregateDataFrame = {
    new AggregateDataFrame(this, cols)
  }

  /**
    * 指定维度增加合计
    *
    * @param aggregate
    * @param axis 默认行维统计
    */
  def summarize(aggregate: NumericAggregate, axis: Int = 0): DataFrame = ???

  def agg(aggs: Seq[(Aggregate[_], AnyRef)]): DataFrame = groupBy().agg(aggs)

  /**
    * 统计df的行数
    *
    * @return
    */
  def count(): Int = length


  def take(offset: Int, limit: Int): DataFrame = ???

  /**
    * 复制元数据
    *
    * @return
    */
  def copyEmpty(): DataFrame = {
    new DataFrame(name, index, columns)
  }

  def copy(): DataFrame = {
    new DataFrame()
  }


  private def copyToDest(selection: BitSet, origin: DataFrame, dest: DataFrame): Unit = {
    for (column <- columns) {

    }
  }

  def length: Int = {
    data.length
  }

  override def iterator: Iterator[List[Any]] = iterrows

  /**
    * 按行遍历，通过Views实现转置
    *
    * @return
    */
  def iterrows: Iterator[List[Any]] = new Views.ListView(this, true).listIterator.asScala

}

object DataFrame {

}
