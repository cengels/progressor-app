package com.cengels.progressor.units

import com.cengels.progressor.enums.ProgressType

import java.util.HashMap

/** A static class comprising several predefined unit constant strings along with some convenience methods for correlating them to a [ProgressType]. */
object Unit {
    const val NONE: String = ""
    const val PERCENT: String = "%"
    const val TONS: String = "t"
    const val KILOGRAMS: String = "kg"
    const val GRAMS: String = "g"
    const val LITERS: String = "l"
    const val MILLILITERS: String = "ml"
    const val KILOMETERS: String = "km"
    const val METERS: String = "m"
    const val CENTIMETERS: String = "cm"
    const val MILLIMETERS: String = "mm"
    const val KILOCALORIES: String = "kcal"
    const val CALORIES: String = "cal"
    const val YEARS: String = "years"
    const val MONTHS: String = "months"
    const val DAYS: String = "days"
    const val WEEKS: String = "wks"
    const val HOURS: String = "h"
    const val MINUTES: String = "min"
    const val SECONDS: String = "s"

    val conversionTable: Map<String, Double>
        get() = this._conversionTable
    private val _conversionTable: MutableMap<String, Double> = HashMap()

    init {
        _conversionTable[Unit.KILOGRAMS] = 1000.0
        _conversionTable[Unit.GRAMS] = 1000.0
        _conversionTable[Unit.MILLILITERS] = 1000.0
        _conversionTable[Unit.METERS] = 1000.0
        _conversionTable[Unit.CENTIMETERS] = 100.0
        _conversionTable[Unit.MILLIMETERS] = 10.0
        _conversionTable[Unit.CALORIES] = 1000.0
        _conversionTable[Unit.MONTHS] = 12.0
        _conversionTable[Unit.WEEKS] = 4.34523809524 // average number of days/month divided by days/week
        _conversionTable[Unit.DAYS] = 7.0
        _conversionTable[Unit.HOURS] = 24.0
        _conversionTable[Unit.MINUTES] = 60.0
        _conversionTable[Unit.SECONDS] = 60.0
    }

    fun getSmallestUnit(progressType: ProgressType): String? = this.getUnits(progressType)?.get(0)

    fun getUnits(progressType: ProgressType): Array<out String>? {
        return try {
            ProgressType::class.java.getField(progressType.name).getAnnotation(UnitDescriptor::class.java).value
        } catch (exception: Exception) {
            null
        }
    }

    fun getProgressType(unit: String): ProgressType {
        for (value in ProgressType.values()) {
            try {
                if (this.getUnits(value)?.contains(unit) == true) {
                    return value
                }
            } catch (ignored: Exception) { }
        }

        return ProgressType.CUSTOM
    }
}
