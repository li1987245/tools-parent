package io.github.tools.dataframe.aggregate

import com.typesafe.scalalogging.Logger
import io.github.tools.dataframe.DataFrame
import io.github.tools.dataframe.column.{Column, NumericColumn}
import io.github.tools.dataframe.function.{Aggregate, DefaultKeyFunction, KeyFunction}
import io.github.tools.dataframe.selection.{BitSet, RoaringBitmapWrap, Selection}
import io.github.tools.dataframe.utils.Widget._

import scala.collection.immutable.ListMap

/**
  * 用于聚合运算
  * 遍历df，使用key function生成ListMap key, value为row index bitmap
  * key function 默认为[col1,col2]数组
  *
  * @param df
  * @param cols
  * @param keyFunction
  */
class AggregateDataFrame(val df: DataFrame, cols: Seq[Int])(implicit keyFunction: KeyFunction[Any, Seq[Any]] = new DefaultKeyFunction) {

  lazy val groups: ListMap[AnyRef, BitSet] = apply(keyFunction)
  private[this] val logger = Logger(this.getClass)

  def apply(keyFunction: KeyFunction[Any, Seq[Any]]): ListMap[AnyRef, BitSet] = {
    var tmp: ListMap[AnyRef, BitSet] = new ListMap[AnyRef, BitSet]
    for (row <- df.zipWithIndex) {
      val list = row._1
      val idx = row._2
      val key = keyFunction(Nil ++ cols.map(list(_)))
      if (tmp.contains(key)) {
        tmp(key).append(idx)
      }
      else {
        val bitSet = new RoaringBitmapWrap()
        bitSet.append(idx)
        tmp = tmp + (key -> bitSet)
      }
    }
    tmp
  }

  def agg(aggExprs: Seq[(Aggregate[_], AnyRef)]): DataFrame = {
    val tmp = new DataFrame()
    //增加分组列信息
    for (col <- cols) tmp.addColumn(df.columns.name(col), df.getColumn(col).copyEmpty)
    //增加统计列信息
    for (expr <- aggExprs) tmp.addColumn(aggColumnName(expr._2, expr._1.name), df.getColumn(expr._2).copyEmpty)
    for (group <- groups) {
      //维度列
      group._1 match {
        case dims: Seq[Any] =>
          dims.zipWithIndex.foreach(dim => tmp.getColumn(dim._2).addObj(dim._1))
        case _ =>
          logger.warn("key function not support")
      }
      //迭代原始DF计算指标
      for (measure <- aggExprs.zipWithIndex) {
        val aggFunction = measure._1._1
        val value = df.getColumn(measure._1._2).slice(group._2) match {
          case numericColumn: NumericColumn[_] => aggFunction.apply(numericColumn)
          case column: Column[_] => column.missingValue
        }
        tmp.getColumn(measure._2).addObj(value)
      }
    }
    tmp
  }


  def explode: Map[AnyRef, DataFrame] = {
    var explodeMap = Map[AnyRef, DataFrame]()
    //截取行范围数据生成新的DF
    for (group <- groups) explodeMap = explodeMap + (group._1 -> Selection.slice(df, group._2))
    explodeMap
  }

  /**
    * 生成df
    * TODO 用于延迟计算，toDF触发计算
    *
    * @param aggExprs
    * @return
    */
  private[this] def toDF(aggExprs: Seq[Aggregate[_]]): DataFrame = ???


}

object AggregateDataFrame {
  def main(args: Array[String]): Unit = {
    val df = new DataFrame()
    val adf = new AggregateDataFrame(df, Array(1, 2))
    import io.github.tools.dataframe.aggregate.NumericAggregates._
    adf.agg(Seq((sum, df.getColumn(1).asInstanceOf[NumericColumn[AnyVal]])))
  }
}

