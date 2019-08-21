package com.cengels.progressor.extensions

/**
 * Does a bitwise check on whether the specified flag value is included in this integer.
 */
fun Int.includes(value: Int): Boolean = this and value == value