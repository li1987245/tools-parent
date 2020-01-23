package io.github.tools.dataframe.join

object JoinType extends Enumeration {

  type JoinType = Value

  val INNER: JoinType.Value = Value("inner")
  val LEFT_JOIN: JoinType.Value = Value("LEFT_OUTER_JOIN")
  val RIGHT_JOIN: JoinType.Value = Value("RIGHT_OUTER_JOIN")
  val FULL_OUTER_JOIN: JoinType.Value = Value("FULL_OUTER_JOIN")

}
