package io.github.tools.dataframe.selection

import io.github.tools.dataframe.DataFrame
import io.github.tools.dataframe.function.Predicate
import io.github.tools.dataframe.internal.{BlockManager, Index}
import io.github.tools.dataframe.column.Column

/**
  * 获取数据切片
  */
object Selection {

  def select(df: DataFrame, predicate: Predicate[List[Any]]): BitSet = {
    val selected = new RoaringBitmapWrap()
    for (row <- df.zipWithIndex) {
      //判断是否满足条件
      if (predicate.apply(row._1.asInstanceOf[List[Any]])) selected.append(row._2)
    }
    selected
  }

  def select[T](index: Index[T], selected: Seq[Int]): Index[T] = {
    val idx = new Index[T]
    for (i <- selected) {
      idx.add(index.name(i))
    }
    idx
  }

  def select[T](index: Index[T], selected: BitSet): Index[T] = {
    val idx = new Index[T]
    for (i <- selected) {
      idx.add(index.name(i))
    }
    idx
  }

  def select(blocks: BlockManager, rows: BitSet, cols: BitSet): BlockManager = {
    val data = new BlockManager
    for (col <- cols) {
      val column: Column[_] = blocks.getColumn(col)
      val _column: Column[_] = column.copyEmpty
      for (row <- rows) {
        _column.addObj(column.get(row))
      }
      data.addColumn(_column)
    }
    data
  }

  /**
    * 按行列进行数据切片，生成新的DataFrame
    *
    * @param df
    * @param rows
    * @param cols
    * @return
    */
  def slice(df: DataFrame, rows: Array[Int], cols: Array[Int]): DataFrame = {
    val result = new DataFrame
    val columns = df.columns
    for (col <- cols) {
      val column = df.getColumn(col)
      val _column = column.copyEmpty
      for (row <- rows) {
        _column.addObj(column.get(row))
      }
      result.addColumn(columns.name(col), _column)
    }
    result
  }

  def slice(df: DataFrame, rows: BitSet): DataFrame = {
    val result = new DataFrame
    val columns = df.columns
    for (col <- columns) {
      val column = df.getColumn(col._2)
      val _column = column.slice(rows)
      result.addColumn(col._1, _column)
    }
    result
  }

}