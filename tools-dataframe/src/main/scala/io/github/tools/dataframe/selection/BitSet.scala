package io.github.tools.dataframe.selection

trait BitSet extends Iterable[Int] {

  def toArray: Array[Int]

  /**
    * Adds the given integers to the Selection if it is not already present, and does nothing
    * otherwise
    */
  def append(ints: Int*): BitSet

  /**
    * Adds to the current bitmap all integers in [rangeStart,rangeEnd)
    *
    * @param start inclusive beginning of range
    * @param end   exclusive ending of range
    */
  def addRange(start: Int, end: Int): BitSet

  def removeRange(start: Long, end: Long): BitSet

  def size: Int

  /**
    * 交集
    *
    * @param otherBitSet
    * @return
    */
  def and(otherBitSet: BitSet): BitSet

  /**
    * 并集
    *
    * @param otherBitSet
    * @return
    */
  def or(otherBitSet: BitSet): BitSet

  /**
    * 返回在当前BitSet中，不在otherBitSet的数据
    *
    * @param otherBitSet
    * @return
    */
  def andNot(otherBitSet: BitSet): BitSet

  def isEmpty: Boolean

  def clear: BitSet

  def contains(i: Int): Boolean

  def get(i: Int): Int

  def sample(n: Int, max: Int): BitSet

  //  def nextSetBit(from: Int): Int

  /**
    * 翻转目标范围内数值
    *
    * @param rangeStart
    * @param rangeEnd
    * @return
    */
  def flip(rangeStart: Int, rangeEnd: Int): BitSet

  /**
    * 位值为true的个数
    *
    * @return
    */
  def cardinality(): Int

  def withoutRange(excludedRangeStart: Int, excludedRangeEnd: Int): BitSet

}