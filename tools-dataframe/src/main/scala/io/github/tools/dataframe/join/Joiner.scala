package io.github.tools.dataframe.join

import java.util

import io.github.tools.dataframe.DataFrame
import io.github.tools.dataframe.join.JoinType.JoinType
import io.github.tools.dataframe.selection.BitSet

import scala.collection.immutable.ListMap

class Joiner(left: DataFrame, leftColumnNames: String*) {

  /**
    *
    * 当rightColumnNames为空或者size为0时，使用leftColumnNames关联左右表，默认为
    *
    * @param right
    * @param joinType
    * @param rightColumnNames
    * @return
    */
  def joinInternal(right: DataFrame, joinType: JoinType, rightColumnNames: String*): DataFrame = {
    val df = left.copyEmpty()
    val leftMap: Map[AnyRef, BitSet] = new ListMap[AnyRef, BitSet]
    val rightMap: Map[AnyRef, BitSet] = new ListMap[AnyRef, BitSet]
    //遍历df，采用ColumnNames对应value生成map key

    df
  }
}
