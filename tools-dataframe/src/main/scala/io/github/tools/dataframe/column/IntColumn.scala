package io.github.tools.dataframe.column

import it.unimi.dsi.fastutil.ints.IntArrayList

class IntColumn(name: String) extends NumericColumn[Int] {
  val data: IntArrayList = new IntArrayList(DEFAULT_ARRAY_SIZE)

}


object IntColumn {
  /**
    * 等同于IntColumn.apply
    * @param name
    * @return
    */
  def create(name: String): IntColumn = {
    IntColumn(name)
  }
}
