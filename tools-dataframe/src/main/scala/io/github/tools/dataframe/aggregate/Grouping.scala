package io.github.tools.dataframe.aggregate

import io.github.tools.dataframe.selection.Selection

import scala.collection.immutable.{ListMap, ListSet}

/**
  * 遍历df，使用key function生成ListMap key, value为row index bitmap
  * key function 默认为[col1,col2]数组
  */
class Grouping {
  private val groups = new ListMap[AnyRef, Selection]
  private val columns = new ListSet[Integer]
}
