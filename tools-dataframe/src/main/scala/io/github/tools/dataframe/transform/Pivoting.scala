package io.github.tools.dataframe.transform

import io.github.tools.dataframe.DataFrame
import io.github.tools.dataframe.aggregate.AggregateDataFrame
import io.github.tools.dataframe.column.{Column, NumericColumn}
import io.github.tools.dataframe.function.{Aggregate, Function}
import io.github.tools.dataframe.selection.Selection
import io.github.tools.dataframe.utils.Widget._
import scala.collection.JavaConverters._
import scala.collection.mutable


object Pivoting {
  def pivot(df: DataFrame, rows: Array[Int], cols: Array[Int], values: Array[Int]): DataFrame = ???


  /**
    * allocate row -> column -> data maps
    *
    * @param df
    * @param rows
    * @param cols
    * @param values
    * @param funcs 对values进行计数的方式
    * @return
    */
  def pivot(df: DataFrame, rows: Array[Int], cols: Array[Int], values: Array[Int], funcs: Map[Int, Array[Function[_, _]]]): DataFrame = {
    //行维列名
    val rowDimIndex = Selection.select(df.columns, rows)
    //列维列名
    val colDimIndex = Selection.select(df.columns, cols)
    //列头
    val pivotCols = mutable.Set[Any]()
    //指标列名
    val measureDimIndex = Selection.select(df.columns, values).map(measure => {
      if (funcs.contains(measure._2)) {
        funcs.get(measure._2).map(aggColumnName(measure._1, _))
      }
      else {
        measure._1
      }
    })
    //数据 row -> column -> values
    val pivotData = mutable.Map[AnyRef, mutable.Map[AnyRef, mutable.ListBuffer[Any]]]()
    //按行维分组
    val grouped: AggregateDataFrame = df.groupBy(rows)
    val explodeDF: Map[AnyRef, DataFrame] = grouped.explode
    //遍历行分组信息
    for (rowExploded <- explodeDF) {
      //行
      val rowValues = rowExploded._1
      //按列维分组
      val _grouped: AggregateDataFrame = rowExploded._2.groupBy(cols)
      val _explodeDF: Map[AnyRef, DataFrame] = _grouped.explode
      for (colExploded <- _explodeDF) {
        //列
        val colValues = colExploded._1
        for (vIdx <- values) {
          val measureValues = pivotData.getOrElse(rowValues, mutable.Map[AnyRef, mutable.ListBuffer[Any]]())
            .getOrElse(colValues, mutable.ListBuffer[Any]())
          val colExplodedDF = colExploded._2
          val _measureValues: Iterable[Any] = colExplodedDF.getColumn(vIdx) match {
            case numericColumn: NumericColumn[_] =>
              //如果需要聚合运算
              if (funcs.contains(vIdx)) {
                funcs.values.map(f => {
                  f.asInstanceOf[Aggregate[_]].apply(numericColumn)
                })
              }
              else {
                //不需要计算，默认返回第一条数据
                Array(numericColumn.get(0))
              }
            case column: Column[_] => Array(column.get(0))
          }
          measureValues ++= _measureValues
        }

      }
    }
    val pivot = new DataFrame()
    pivot
  }
}