package io.github.tools.dataframe.selection

import it.unimi.dsi.fastutil.ints.IntIterable

trait Selection extends IntIterable {

  def toArray: Array[Int]

  /**
    * Adds the given integers to the Selection if it is not already present, and does nothing
    * otherwise
    */
  def add(ints: Int*): Selection

  /**
    * Adds to the current bitmap all integers in [rangeStart,rangeEnd)
    *
    * @param start inclusive beginning of range
    * @param end   exclusive ending of range
    */
  def addRange(start: Int, end: Int): Selection

  def removeRange(start: Long, end: Long): Selection

  def size: Int

  /**
    * Returns the intersection of the receiver and {@code otherSelection}, after updating the
    * receiver
    */
  def and(otherSelection: Selection): Selection

  /** Returns the union of the receiver and {@code otherSelection}, after updating the receiver */
  def or(otherSelection: Selection): Selection

  /**
    * Implements the set difference operation between the receiver and {@code otherSelection}, after
    * updating the receiver
    */
  def andNot(otherSelection: Selection): Selection

  def isEmpty: Boolean

  def clear: Selection

  def contains(i: Int): Boolean

  /**
    * Returns the value of the ith element. For example, if there are three ints {4, 32, 71} in the
    * selection, get(0) returns 4, get(1) returns 32, and get(2) returns 71
    *
    * <p>It can be useful if you need to iterate over the data, although there is also an iterator
    */
  def get(i: Int): Int

  /** Returns a selection with the bits from this selection flipped over the given range */
  def flip(rangeStart: Int, rangeEnd: Int): Selection
}