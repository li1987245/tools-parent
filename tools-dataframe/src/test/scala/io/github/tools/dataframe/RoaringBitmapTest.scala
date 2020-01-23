package io.github.tools.dataframe

import org.roaringbitmap.RoaringBitmap
import org.junit.Test

class RoaringBitmapTest {
  @Test
  def testBitMap(): Unit = {
    import scala.jdk.CollectionConverters._

    val rr: RoaringBitmap = RoaringBitmap.bitmapOf(1, 2, 3, 1000)
    val rr2 = new RoaringBitmap
    rr2.add(4000L, 4255L)
    println(rr2)
    rr.select(3) // would return the third value or 1000

    rr.rank(2) // would return the rank of 2, which is index 1

    rr.contains(1000) // will return true

    rr.contains(7) // will return false


    val rror = RoaringBitmap.or(rr, rr2) // new bitmap
    rr.or(rr2) //in-place computation

    val equals = rror == rr // true
    if (!equals) throw new RuntimeException("bug")
    // number of values stored?
    val cardinality = rr.getLongCardinality
    println(cardinality)
    // a "forEach" is faster than this loop, but a loop is possible:
    for (i <- rr.asScala ){
      println(i)
    }
  }

}
