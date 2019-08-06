package com.cengels.progressor

import com.cengels.progressor.enums.ProgressType
import com.cengels.progressor.units.Unit
import com.cengels.progressor.units.UnitValue
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

import java.util.stream.Stream

import org.assertj.core.api.Assertions.assertThat

/**
 * Tests the [UnitValue] class.
 */
class UnitValueTest {
    @ParameterizedTest
    @MethodSource
    fun constructor_ProgressType(progressType: ProgressType, unit: String) {
        assertThat(UnitValue(progressType).unit).isEqualTo(unit)
    }

    @ParameterizedTest
    @MethodSource("constructor_unitDecimals")
    fun constructor_unitDecimalsDouble(value: Double, unit: String, decimals: Int) {
        assertThat(UnitValue(value, unit).decimals).isEqualTo(decimals)
    }

    @ParameterizedTest
    @MethodSource("constructor_unitDecimals")
    fun constructor_unitDecimalsInt(value: Double, unit: String, decimals: Int) {
        assertThat(UnitValue(value.toInt(), unit).decimals).isEqualTo(decimals)
    }

    @ParameterizedTest
    @MethodSource
    fun constructor_progressTypeDecimals(progressType: ProgressType, decimals: Int) {
        assertThat(UnitValue(progressType).decimals).isEqualTo(decimals)
    }

    @ParameterizedTest
    @MethodSource
    fun stringify_bestUnit(value: UnitValue, formattedValue: String) {
        assertThat(value.toString()).isEqualTo(formattedValue)
    }

    @ParameterizedTest
    @MethodSource
    fun stringify_time(value: UnitValue, formattedValue: String) {
        assertThat(value.toString()).isEqualTo(formattedValue)
    }

    companion object {
        @JvmStatic
        private fun stringify_time(): Stream<Arguments> {
            return Stream.of(
                    Arguments.of(UnitValue(530, Unit.SECONDS), "8:50"),
                    Arguments.of(UnitValue(3800, Unit.SECONDS), "1:03:20"),
                    Arguments.of(UnitValue(133, Unit.MINUTES), "2:13:00")
            )
        }

        @JvmStatic
        private fun constructor_ProgressType(): Stream<*> {
            return Stream.of(
                    Arguments.of(ProgressType.COUNT, Unit.NONE),
                    Arguments.of(ProgressType.PERCENTAGE, Unit.PERCENT),
                    Arguments.of(ProgressType.WEIGHT, Unit.GRAMS),
                    Arguments.of(ProgressType.DISTANCE, Unit.MILLIMETERS),
                    Arguments.of(ProgressType.HEIGHT, Unit.MILLIMETERS),
                    Arguments.of(ProgressType.LIQUID, Unit.MILLILITERS),
                    Arguments.of(ProgressType.CALORIES, Unit.CALORIES),
                    Arguments.of(ProgressType.TIME, Unit.SECONDS),
                    Arguments.of(ProgressType.CUSTOM, Unit.NONE)
            )
        }

        @JvmStatic
        private fun constructor_unitDecimals(): Stream<Arguments> {
            return Stream.of(
                    Arguments.of(5.0, Unit.SECONDS, 0),
                    Arguments.of(2.0, Unit.CALORIES, 2),
                    Arguments.of(50.0, Unit.PERCENT, 1),
                    Arguments.of(50.0, Unit.NONE, 0),
                    Arguments.of(80.0, "test", 2)
            )
        }

        @JvmStatic
        private fun constructor_progressTypeDecimals(): Stream<Arguments> {
            return Stream.of(
                    Arguments.of(ProgressType.CALORIES, 2),
                    Arguments.of(ProgressType.COUNT, 0),
                    Arguments.of(ProgressType.CUSTOM, 2),
                    Arguments.of(ProgressType.DISTANCE, 2),
                    Arguments.of(ProgressType.HEIGHT, 2),
                    Arguments.of(ProgressType.LIQUID, 2),
                    Arguments.of(ProgressType.PERCENTAGE, 1),
                    Arguments.of(ProgressType.TIME, 0),
                    Arguments.of(ProgressType.WEIGHT, 2)
            )
        }

        @JvmStatic
        private fun stringify_bestUnit(): Stream<Arguments> {
            return Stream.of(
                    Arguments.of(UnitValue(5000, Unit.CENTIMETERS), "50m"),
                    Arguments.of(UnitValue(5050, Unit.CENTIMETERS), "50.50m"),
                    Arguments.of(UnitValue(4222, Unit.NONE), "4222"),
                    Arguments.of(UnitValue(42.53, Unit.PERCENT), "42.5%"),
                    Arguments.of(UnitValue(4222, "tst"), "4222tst"),
                    Arguments.of(UnitValue(4222, "tst", 5), "4222tst"),
                    Arguments.of(UnitValue(4222, "tst", 0), "4222tst"),
                    Arguments.of(UnitValue(4222.5, "tst"), "4222.50tst"),
                    Arguments.of(UnitValue(4222.5, "tst", 5), "4222.50000tst")
            )
        }
    }
}