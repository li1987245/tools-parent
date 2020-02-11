package io.github.tools.dataframe.utils

object ImplicitConversions {
    implicit def asInt( _in:Option[Any] ): Option[Int] = _in.asInstanceOf[Option[Int]]
    implicit def asDouble( _in:Option[Any] ): Option[Double] = _in.asInstanceOf[Option[Double]]
}