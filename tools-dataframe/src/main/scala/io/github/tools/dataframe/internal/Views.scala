package io.github.tools.dataframe.internal

import java.util

import io.github.tools.dataframe.DataFrame
import scala.collection.JavaConverters._

object Views {

  /**
    *
    * @param df
    * @param transpose 转置
    */
  class ListView(val df: DataFrame, val transpose: Boolean) extends util.AbstractList[List[Any]] {

    override def get(index: Int) = new Views.SeriesListView(df, index, !transpose).asScala.toList

    def size: Int = if (transpose) df.length
    else df.size
  }

  class SeriesListView(val df: DataFrame, val index: Int, val transpose: Boolean) extends util.AbstractList[Any] {

    override def get(index: Int): Any = if (transpose) df.get(index, this.index)

    else df.get(this.index, index)

    override def size: Int = if (transpose) df.length

    else df.size
  }

}