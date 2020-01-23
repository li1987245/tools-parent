package io.github.tools.dataframe

import org.junit.Test

class CollectionTest {
  @Test
  def testBitMap(): Unit = {
    List("a c d f", "a b c e", "a a").view.map(_.split(" ")).filter(_.length > 2).flatMap(args => {
      //      for {
      //        x <- args
      //        if !x.isEmpty
      //      } yield (x, 1)
      args.map((_, 1))
    }
    ).groupBy(_._1).mapValues(x => {
      x.map(_._2).sum
    }).foreach(x => {
      println(x)
    })
    println("bit map")
  }
}
