package io.github.tools.dataframe.column

import io.github.tools.dataframe.filter.Filter
import io.github.tools.dataframe.function.Predicate
import io.github.tools.dataframe.selection.{RoaringBitmapWrap, BitSet}

abstract class NumericColumn[T <: AnyVal] extends AbstractColumn[T] with Filter[T] {


  def getDouble(index: Int): Double

  /**
    * 根据predicate表达式生成selection，获得行索引
    *
    * @param predicate
    * @return
    */
  override def eval(predicate: Predicate[T]): BitSet = {
    val selection: BitSet = new RoaringBitmapWrap()
    for ((element, idx) <- this.zipWithIndex) {
      if (predicate(element)) {
        selection.append(idx)
      }
    }
    selection
  }

  def asDoubleArray: Array[Double] = {
    (for (i <- 0 until size) yield getDouble(i)).toArray
  }
}
