package com.cengels.progressor.units

import com.cengels.progressor.enums.ProgressType
import com.cengels.progressor.exceptions.IllegalConversionException

class Convert private constructor(val value: UnitValue) {
    private var units: Array<out String>? = null
    private var checked: Boolean = false

    private val progressType: ProgressType
        get() = Unit.getProgressType(this.value.unit)

    private fun getUnits(): Array<out String>? {
        if (this.units == null && !this.checked) {
            this.units = Unit.getUnits(this.progressType)
            this.checked = true
        }

        return this.units
    }

    /** Converts the [UnitValue] to the specified [unit string][Unit]. */
    fun to(toUnit: String): UnitValue {
        if (this.value.unit == toUnit) {
            return this.value
        }

        val units = this.getUnits() ?: throw IllegalConversionException(this.value.unit, toUnit)

        val index = units.indexOf(this.value.unit)
        val indexTarget = units.indexOf(toUnit)

        if (index == -1 || indexTarget == -1) {
            throw IllegalConversionException(this.value.unit, toUnit)
        }

        var value = this.value.get()

        if (index < indexTarget) {
            for (i in index until indexTarget) {
                value /= Unit.conversionTable.getValue(units[i])
            }
        } else {
            for (i in index downTo indexTarget + 1) {
                value *= Unit.conversionTable.getValue(units[i - 1])
            }
        }

        return UnitValue(value, toUnit)
    }

    /**
     * Gets a new [UnitValue] with the unit that corresponds to the lowest possible number of digits.
     *
     * Example: 1,000m -> 1km
     */
    fun best(): UnitValue {
        val units = this.getUnits() ?: return this.value

        var index = units.indexOf(this.value.unit)

        var value = this.value.get()
        var unit = this.value.unit

        while (index <= units.size - 1 && value < 1 && Unit.conversionTable.containsKey(units[index - 1]) || index >= 0 && Unit.conversionTable.containsKey(unit) && value >= Unit.conversionTable[unit]!!) {
            if (value < 1) {
                value *= Unit.conversionTable.getValue(units[index - 1])
                index--
            } else {
                value /= Unit.conversionTable.getValue(unit)
                index++
            }

            unit = units[index]
        }

        return UnitValue(value, unit)
    }

    companion object {
        /** Prepares a [Convert] builder that converts from the specified [Double] and [unit string][Unit]. */
        fun from(value: Double, fromUnit: String): Convert = Convert(UnitValue(value, fromUnit))

        /** Prepares a [Convert] builder that converts from the specified [UnitValue]. */
        fun from(unitValue: UnitValue): Convert = Convert(unitValue)
    }
}
