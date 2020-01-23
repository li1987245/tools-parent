package io.github.tools.dataframe.selection

import com.google.common.base.Preconditions
import it.unimi.dsi.fastutil.ints.IntIterator
import org.roaringbitmap.RoaringBitmap
import java.util
import java.util.Random
import scala.collection.JavaConverters._

object BitmapBackedSelection {

  protected def append(rows: Int*): Selection = {
    val selection = new BitmapBackedSelection
    for (i <- rows) {
      selection.add(i)
    }
    selection
  }

  protected def withRange(start: Int, end: Int): Selection = {
    val selection = new BitmapBackedSelection
    selection.addRange(start, end)
    selection
  }

  protected def withoutRange(totalRangeStart: Int, totalRangeEnd: Int, excludedRangeStart: Int, excludedRangeEnd: Int): Selection = {
    Preconditions.checkArgument(excludedRangeStart >= totalRangeStart)
    Preconditions.checkArgument(excludedRangeEnd <= totalRangeEnd)
    Preconditions.checkArgument(totalRangeEnd >= totalRangeStart)
    Preconditions.checkArgument(excludedRangeEnd >= excludedRangeStart)
    val selection = this.withRange(totalRangeStart, totalRangeEnd)
    val exclusion = this.withRange(excludedRangeStart, excludedRangeEnd)
    selection.andNot(exclusion)
    selection
  }

  /** Returns an randomly generated selection of size N where Max is the largest possible value */
  protected def selectNRowsAtRandom(n: Int, max: Int): Selection = {
    val selection = new BitmapBackedSelection
    if (n > max) throw new IllegalArgumentException("Illegal arguments: N (" + n + ") greater than Max (" + max + ")")
    val rows = new Array[Int](n)
    if (n == max) {
      var k = 0
      while ( {
        k < n
      }) {
        selection.add(k) {
          k += 1
          k
        }
      }
      return selection
    }
    val bs = new util.BitSet(max)
    var cardinality = 0
    val random = new Random
    while ( {
      cardinality < n
    }) {
      val v = random.nextInt(max)
      if (!bs.get(v)) {
        bs.set(v)
        cardinality += 1
      }
    }
    var pos = 0
    var i = bs.nextSetBit(0)
    while ( {
      i >= 0
    }) {
      rows({
        pos += 1
        pos - 1
      }) = i
      i = bs.nextSetBit(i + 1)
    }
    for (row <- rows) {
      selection.add(row)
    }
    selection
  }
}

class BitmapBackedSelection() extends Selection {

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
    add(arr: _*)
  }

  def this(bitmap: RoaringBitmap) {
    this()
    this.bitmap = bitmap
  }

  override def removeRange(start: Long, end: Long): Selection = {
    this.bitmap.remove(start, end)
    this
  }

  override def flip(rangeStart: Int, rangeEnd: Int): Selection = {
    this.bitmap.flip(rangeStart.toLong, rangeEnd)
    this
  }

  def add(ints: Int*): Selection = {
    //scala 数组转换为 java 可变长形参
    //Array[String]("name","age"):_*      =>    String... ms
    bitmap.add(ints: _*)
    this
  }

  override def toString: String = "Selection of size: " + bitmap.getCardinality

  override def size: Int = bitmap.getCardinality

  override def toArray: Array[Int] = bitmap.toArray

  private def toBitmap(otherSelection: Selection): RoaringBitmap = {
    otherSelection match {
      case selection: BitmapBackedSelection => return selection.bitmap.clone
      case _ =>
    }
    val bits = new RoaringBitmap
    import scala.collection.JavaConversions._
    for (i <- otherSelection) {
      bits.add(i)
    }
    bits
  }

  override def and(otherSelection: Selection): Selection = {
    bitmap.and(toBitmap(otherSelection))
    this
  }

  override def or(otherSelection: Selection): Selection = {
    bitmap.or(toBitmap(otherSelection))
    this
  }

  override def andNot(otherSelection: Selection): Selection = {
    bitmap.andNot(toBitmap(otherSelection))
    this
  }

  override def isEmpty: Boolean = size == 0

  override def clear: Selection = {
    bitmap.clear()
    this
  }

  override def contains(i: Int): Boolean = bitmap.contains(i)

  override def addRange(start: Int, end: Int): Selection = {
    bitmap.add(start.toLong, end)
    this
  }

  override def get(i: Int): Int = bitmap.select(i)

  override def equals(o: Any): Boolean = {
    if (super.equals(o)) return true
    if (o == null || (getClass ne o.getClass)) return false
    val other = o.asInstanceOf[BitmapBackedSelection]
    bitmap == other.bitmap
  }

  override def hashCode: Int = bitmap.hashCode

  /** Returns a fastUtil intIterator that wraps a bitmap intIterator */
  override def iterator: IntIterator = new IntIterator() {

    final private val iterator = bitmap.getIntIterator

    override def nextInt: Int = iterator.next

    override def skip(k: Int) = throw new UnsupportedOperationException("Views do not support skipping in the iterator")

    override def hasNext: Boolean = iterator.hasNext
  }
}