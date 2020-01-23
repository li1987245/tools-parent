package io.github.tools.dataframe.internal

import io.github.tools.dataframe.column.Column
import it.unimi.dsi.fastutil.objects.ObjectArrayList

class BlockManager {
    //按列存储数据，通过索引定位
    private var blocks:ObjectArrayList[Column] = new ObjectArrayList[Column]()

    def set(value: V, col: Int, row: Int): Unit = {
        blocks.get(col).set(row, value)
    }

    def append
}
