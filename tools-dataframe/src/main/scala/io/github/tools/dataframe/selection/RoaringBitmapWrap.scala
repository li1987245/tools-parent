package io.github.tools.dataframe.selection

import java.util
import java.util.Random

import org.roaringbitmap.RoaringBitmap

object RoaringBitmapWrap {

  def create(start: Int, end: Int): BitSet = {
    val bitmap = new RoaringBitmapWrap
    bitmap.addRange(start, end)
    bitmap
  }
}

class RoaringBitmapWrap() extends BitSet {

  final private var bitmap: RoaringBitmap = new RoaringBitmap

  /**
    * Returns a selection initialized from 0 to the given size, which cane be used for queries that
    * exclude certain items, by first selecting the items to exclude, then flipping the bits.
    *
    * @param size The size The end point, exclusive
    */
  def this(size: Int) {
    this()
    addRange(0, size)
  }

  def this(arr: Int*) {
    this()
    append(arr: _*)
  }

  def this(bitmap: RoaringBitmap) {
    this()
    this.bitmap = bitmap
  }

  override def removeRange(start: Long, end: Long): BitSet = {
    this.bitmap.remove(start, end)
    this
  }

  override def flip(rangeStart: Int, rangeEnd: Int): BitSet = {
    this.bitmap.flip(rangeStart.toLong, rangeEnd)
    this
  }

  override def append(ints: Int*): BitSet = {
    //scala 数组转换为 java 可变长形参
    //Array[String]("name","age"):_*      =>    String... ms
    bitmap.add(ints: _*)
    this
  }

  override def withoutRange(excludedRangeStart: Int, excludedRangeEnd: Int): BitSet = {
    val _bitmap: RoaringBitmapWrap = new RoaringBitmapWrap
    val exclusion = _bitmap.addRange(excludedRangeStart, excludedRangeEnd)
    this.andNot(_bitmap)
    this
  }

  /**
    * 默认有放回获取<=n个数
    *
    * @param n
    * @param max
    * @return
    */
  override def sample(n: Int, max: Int): BitSet = {
    val bitmap = new RoaringBitmapWrap
    if (n > max) throw new IllegalArgumentException("Illegal arguments: N (" + n + ") greater than Max (" + max + ")")
    val rows = new Array[Int](n)
    if (n == max) {
      //copy this
      for (k <- 0 until max) {
        bitmap.append(get(k))
      }
      return bitmap
    }
    val random = new Random
    for (k <- 0 until n) {
      val i = random.nextInt(max)
      bitmap.append(get(i))
    }
    bitmap
  }

  override def toString: String = "bitmap of size: " + bitmap.getCardinality

  override def size: Int = bitmap.getCardinality

  override def toArray: Array[Int] = bitmap.toArray

  private def toBitmap(otherBitSet: BitSet): RoaringBitmap = {
    otherBitSet match {
      case bitmap: RoaringBitmapWrap => return bitmap.bitmap.clone
      case _ =>
    }
    val bits = new RoaringBitmap
    for (i <- otherBitSet) {
      bits.add(i)
    }
    bits
  }

  override def and(otherBitSet: BitSet): BitSet = {
    bitmap.and(toBitmap(otherBitSet))
    this
  }

  override def or(otherBitSet: BitSet): BitSet = {
    bitmap.or(toBitmap(otherBitSet))
    this
  }

  override def andNot(otherBitSet: BitSet): BitSet = {
    bitmap.andNot(toBitmap(otherBitSet))
    this
  }

  override def isEmpty: Boolean = size == 0

  override def clear: BitSet = {
    bitmap.clear()
    this
  }

  override def contains(i: Int): Boolean = bitmap.contains(i)

  override def addRange(start: Int, end: Int): BitSet = {
    bitmap.add(start.toLong, end)
    this
  }

  override def get(i: Int): Int = bitmap.select(i)

  override def equals(o: Any): Boolean = {
    if (super.equals(o)) return true
    if (o == null || (getClass ne o.getClass)) return false
    val other = o.asInstanceOf[RoaringBitmapWrap]
    bitmap == other.bitmap
  }

  override def hashCode: Int = bitmap.hashCode

  override def iterator: Iterator[Int] = new Iterator[Int] {
    final private val iterator = bitmap.getIntIterator

    override def hasNext: Boolean = iterator.hasNext

    override def next(): Int = iterator.next
  }

  /**
    * 位值为true的个数
    *
    * @return
    */
  override def cardinality(): Int = {
    bitmap.getCardinality
  }
}