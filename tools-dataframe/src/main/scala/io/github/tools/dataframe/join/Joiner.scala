package io.github.tools.dataframe.join

import io.github.tools.dataframe.DataFrame
import io.github.tools.dataframe.join.JoinType.JoinType

class Joiner(left: DataFrame, leftColumnNames: String*) {

  /**
    *
    * 当rightColumnNames为空或者size为0时，使用leftColumnNames关联左右表
    * @param right
    * @param joinType
    * @param rightColumnNames
    * @return
    */
  def joinInternal(right: DataFrame, joinType: JoinType, rightColumnNames: String*): DataFrame = {

    left.copyEmpty()
  }
}
