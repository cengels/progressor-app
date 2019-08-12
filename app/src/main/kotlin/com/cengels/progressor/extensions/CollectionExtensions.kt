package com.cengels.progressor.extensions

import java.util.*

fun <E> MutableList<E>.swap(i: Int, j: Int) {
    Collections.swap(this, i, j)
}