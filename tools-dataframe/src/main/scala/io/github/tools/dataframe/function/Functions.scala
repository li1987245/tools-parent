package io.github.tools.dataframe.function

import scala.collection.immutable.HashSet

object Functions {


  class Unique[V] extends Function[List[V], Set[V]] {
    /**
      *
      * @param value
      * @return
      */
    override def apply(value: List[V]): Set[V] = {
      Set[V]() ++ value
    }
  }

  /**
    * 删除空值
    *
    * @tparam V
    */
  class DropNaPredicate[V] extends Predicate[List[V]] {
    def apply(values: List[V]): Boolean = {
      for (value <- values) {
        //Option(value) 相当于 value == null -> None
        Option(value) match {
          case None => return false
        }
      }
      true
    }
  }

}
