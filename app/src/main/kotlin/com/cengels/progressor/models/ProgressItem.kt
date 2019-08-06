package com.cengels.progressor.models

import com.cengels.progressor.enums.ProgressType
import com.cengels.progressor.units.UnitValue

class ProgressItem {
    var id: Int = 0
    var label: String = ""
    lateinit var value: UnitValue
    lateinit var goal: UnitValue
    lateinit var step: UnitValue

    val type: ProgressType
        get() = this.value.type

    val unitLabel: String
        get() = this.value.getBestUnit() + " " + this.label

    val progress: UnitValue
        get() = this.value / this.goal * 100
}
