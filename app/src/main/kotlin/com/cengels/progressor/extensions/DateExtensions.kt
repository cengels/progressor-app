package com.cengels.progressor.extensions

import java.util.*

fun Date.roundToHour(): Date = this.toCalendar().apply { roundToHour() }.time

fun Date.toCalendar(): Calendar = Calendar.getInstance().also { it.time = this }

/** Sets the field to zero and adds 1 to the next-largest field if this field is greater than or equal to half its maximum. */
fun Calendar.round(field: Int) {
    val maximum: Int = this.getMaximum(field) + 1

    if (this[field] >= maximum / 2) {
        this.add(field, maximum - this[field])
    } else {
        this[field] = 0
    }
}

fun Calendar.roundToHour() {
    this.clone().apply {
        round(Calendar.MILLISECOND)
        round(Calendar.SECOND)
        round(Calendar.MINUTE)
    }
}
