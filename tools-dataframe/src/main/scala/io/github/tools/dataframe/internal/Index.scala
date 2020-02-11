package io.github.tools.dataframe.internal

import java.util

import com.google.common.collect.{BiMap, HashBiMap}
import com.typesafe.scalalogging.Logger

import scala.collection.JavaConverters._

class Index[T] extends Iterable[(T, Int)] {
  self =>

  //this别

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

  val add: T => Unit = self.extend

  def add(name: T, idx: Int): Unit = {
    index.put(name, idx)
  }

  /**
    * 默认追加到最后
    *
    * @param name
    */
  def extend(name: T): Unit = {
    add(name, size())
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
    * 根据索引获取列名
    *
    * @param idx
    * @return
    */
  def name(idx: Int): T = {
    index.inverse().get(idx)
  }

  /**
    * 返回索引名称
    *
    * @return
    */
  def names: Set[T] = {
    index.keySet.asScala.toSet
  }

  override def size(): Int = {
    index.size()
  }

  override def iterator: Iterator[(T, Int)] = new Iterator[(T, Int)] {

    val iter: util.Iterator[util.Map.Entry[T, Int]] = index.entrySet().iterator()

    override def hasNext: Boolean = iter.hasNext

    override def next(): (T, Int) = {
      val entry = iter.next()
      (entry.getKey, entry.getValue)
    }
  }

  def main(args: Array[String]): Unit = {
    val idx = new Index[String]()
    idx.add("")
  }
}
