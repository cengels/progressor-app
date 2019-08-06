package com.cengels.progressor.units

import com.cengels.progressor.enums.ProgressType

import java.io.Serializable
import kotlin.math.roundToInt

data class UnitValue private constructor(
        private val value: Double,
        var unit: String,
        var decimals: Int = 2,
        val type: ProgressType
) : Serializable {
    fun getBestUnit(): String? = if (this.type == ProgressType.CUSTOM) this.unit else Convert.from(this).best().unit

    fun getBest(): UnitValue = if (this.type == ProgressType.CUSTOM) this else Convert.from(this).best()

    val formattedValue: String
        get() {
            val value = this.getBest()

            when (value.unit) {
                Unit.SECONDS -> return String.format("0:%02d", value.get().toLong())
                Unit.MINUTES -> return String.format("%d:%02d", value.get().toLong(), (value.get() * SECS_PER_MINUTE % SECS_PER_MINUTE).toLong())
                Unit.HOURS -> return String.format("%d:%02d:%02d", value.get().toLong(), (value.get() * SECS_PER_MINUTE % SECS_PER_MINUTE).toLong(), (value.get() * SECS_PER_MINUTE.toDouble() * SECS_PER_MINUTE.toDouble() % SECS_PER_MINUTE).toLong())
            }

            return if (value.decimals == 0 || value.get().toLong().toDouble() == value.get()) {
                value.get().toLong().toString()
            } else String.format("%." + this.decimals + "f", value.get())

        }

    constructor(value: Double, unit: String, decimals: Int) : this(value, unit, decimals, Unit.getProgressType(unit))
    constructor(value: Double, unit: String) : this(value, unit, -1, Unit.getProgressType(unit)) {
        this.inferDecimals()
    }
    constructor(value: Double, type: ProgressType) : this(value, Unit.getSmallestUnit(type) ?: "", 2, type) {
        this.inferDecimals()
    }

    constructor(value: Int, unit: String, decimals: Int) : this(value.toDouble(), unit, decimals)
    constructor(value: Int, unit: String) : this(value.toDouble(), unit)

    constructor(type: ProgressType) : this(0.0, Unit.getSmallestUnit(type) ?: "", -1, type) {
        this.inferDecimals()
    }
    constructor(unit: String, decimals: Int) : this(0.0, unit, decimals, Unit.getProgressType(unit))

    /** Gets the raw value contained within this [UnitValue]. */
    fun get(): Double = this.value
    /** Gets a rounded int value from the raw value contained within this [UnitValue]. */
    fun getInt(): Int = this.value.roundToInt()

    private fun inferDecimals() {
        when (this.type) {
            ProgressType.COUNT, ProgressType.TIME -> this.decimals = 0
            ProgressType.PERCENTAGE -> this.decimals = 1
            else -> this.decimals = 2
        }
    }

    /** Gets a string representation of the value contained within this [UnitValue]. */
    fun getFormattedValue(unit: String? = null): String {
        val value = if (unit != null) Convert.from(this).to(toUnit = unit) else Convert.from(this).best()

        return if (this.decimals == 0) value.get().toLong().toString() else String.format("%.${this.decimals}f", value.get())
    }

    override fun toString(): String {
        return when (this.getBest().unit) {
            Unit.SECONDS, Unit.MINUTES, Unit.HOURS -> getBest().formattedValue
            else -> getBest().formattedValue + getBest().unit
        }
    }

    fun toString(unit: String): String {
        return when (unit) {
            Unit.SECONDS, Unit.MINUTES, Unit.HOURS -> this.getFormattedValue(unit)
            else -> this.getFormattedValue(unit) + unit
        }
    }

    operator fun times(value: UnitValue): UnitValue = UnitValue(this.value * value.value, this.unit, this.decimals)
    operator fun div(value: UnitValue): UnitValue = UnitValue(this.value / value.value, this.unit, this.decimals)
    operator fun plus(value: UnitValue): UnitValue = UnitValue(this.value + value.value, this.unit, this.decimals)
    operator fun minus(value: UnitValue): UnitValue = UnitValue(this.value - value.value, this.unit, this.decimals)

    operator fun times(value: Double): UnitValue = UnitValue(this.value * value, this.unit, this.decimals)
    operator fun div(value: Double): UnitValue = UnitValue(this.value / value, this.unit, this.decimals)
    operator fun plus(value: Double): UnitValue = UnitValue(this.value + value, this.unit, this.decimals)
    operator fun minus(value: Double): UnitValue = UnitValue(this.value - value, this.unit, this.decimals)

    operator fun times(value: Int): UnitValue = UnitValue(this.value * value, this.unit, this.decimals)
    operator fun div(value: Int): UnitValue = UnitValue(this.value / value, this.unit, this.decimals)
    operator fun plus(value: Int): UnitValue = UnitValue(this.value + value, this.unit, this.decimals)
    operator fun minus(value: Int): UnitValue = UnitValue(this.value - value, this.unit, this.decimals)

    operator fun inc(): UnitValue = this + 1

    companion object {
        const val SECS_PER_MINUTE: Int = 60
    }
}
