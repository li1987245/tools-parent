package io.github.tools.dataframe.index

import java.lang.Iterable
import java.util
import java.util.Map

import scala.collection.JavaConverters._
import com.google.common.collect.{BiMap, HashBiMap}
import com.typesafe.scalalogging.Logger

class Index[T] extends Iterable[util.Map.Entry[T, Int]] {

  private[this] val logger = Logger(this.getClass)
  //双向map，name -> index index -> name
  private val index: BiMap[T, Int] = HashBiMap.create()

  def this(names: Seq[T]) {
    this()
    //names.view会生成视图，使后续对names的操作变为延迟操作
    for ((x, i) <- names.view.zipWithIndex) {
      logger.debug(s"index name: $x, index: $i")
      index.put(x, i)
    }
  }

  /**
    * 默认追加到最后
    *
    * @param name
    */
  def add(name: T): Unit = {
    add(name, index.values().asScala.max)
  }

  def add(name: T, idx: Int): Unit = {
    index.put(name, idx)
  }

  /**
    * 根据索引名称获取索引
    *
    * @param names
    * @return
    */
  def indices(names: T*): Array[Int] = {
    val size = names.size
    val indices = new Array[Int](size)
    for (i <- 0 to size) {
      indices(i) = get(names(i))
    }
    indices
  }

  def get(name: T): Int = {
    index.get(name)
  }

  /**
    * 返回索引名称
    *
    * @return
    */
  def names: Set[T] = {
    index.keySet.asScala.toSet
  }

  override def iterator: util.Iterator[util.Map.Entry[T, Int]] = index.entrySet().iterator()
}
