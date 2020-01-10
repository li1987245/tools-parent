package io.github.tools.dataframe

import scala.collection.mutable.ListBuffer

class DataFrame(name: String) {
  private[this] val columns: List[String] = List()

  def this(name: String, index: List[String], columns: List[String],data) {
    this(name)

  }

}

object DataFrame {

}
