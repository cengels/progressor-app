package com.cengels.progressor;

import com.cengels.progressor.enums.ProgressType;
import com.cengels.progressor.units.Convert;
import com.cengels.progressor.units.Unit;
import com.cengels.progressor.units.UnitValue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests the {@link UnitValue} class.
 */
public class UnitValueTest {
    @ParameterizedTest
    @MethodSource
    public void constructor_ProgressType(final ProgressType progressType, final String unit) {
        assertThat(new UnitValue(progressType).getUnit()).isEqualTo(unit);
    }

    @ParameterizedTest
    @MethodSource("constructor_unitDecimals")
    public void constructor_unitDecimalsDouble(final double value, final String unit, final int decimals) {
        assertThat(new UnitValue(value, unit).getDecimals()).isEqualTo(decimals);
    }

    @ParameterizedTest
    @MethodSource("constructor_unitDecimals")
    public void constructor_unitDecimalsInt(final double value, final String unit, final int decimals) {
        assertThat(new UnitValue((int)value, unit).getDecimals()).isEqualTo(decimals);
    }

    @ParameterizedTest
    @MethodSource
    public void constructor_progressTypeDecimals(final ProgressType progressType, final int decimals) {
        assertThat(new UnitValue(progressType).getDecimals()).isEqualTo(decimals);
    }

    @ParameterizedTest
    @MethodSource
    public void stringify_bestUnit(final UnitValue value, final String formattedValue) {
        assertThat(value.toString()).isEqualTo(formattedValue);
    }

//    @ParameterizedTest
//    @MethodSource
//    public void stringify_time(final UnitValue value, final String formattedValue) {
//        assertThat(value.toString()).isEqualTo(formattedValue);
//    }
//
//    private static Stream stringify_time() {
//        return Stream.of(
//                Arguments.of(new UnitValue(5000, Unit.CENTIMETERS), "50m")
//        );
//    }

    private static Stream constructor_ProgressType() {
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
        );
    }

    private static Stream constructor_unitDecimals() {
        return Stream.of(
            Arguments.of(5.0, Unit.SECONDS, 0),
            Arguments.of(2.0, Unit.CALORIES, 2),
            Arguments.of(50.0, Unit.PERCENT, 1),
            Arguments.of(50.0, Unit.NONE, 0),
            Arguments.of(80.0, "test", 2)
        );
    }

    private static Stream constructor_progressTypeDecimals() {
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
        );
    }

    private static Stream stringify_bestUnit() {
        return Stream.of(
                Arguments.of(new UnitValue(5000, Unit.CENTIMETERS), "50m"),
                Arguments.of(new UnitValue(5050, Unit.CENTIMETERS), "50.50m"),
                Arguments.of(new UnitValue(4222, Unit.NONE), "4222"),
                Arguments.of(new UnitValue(42.53, Unit.PERCENT), "42.5%"),
                Arguments.of(new UnitValue(4222, "tst"), "4222.00 tst"),
                Arguments.of(new UnitValue(4222, "tst", 5), "4222.00000tst"),
                Arguments.of(new UnitValue(4222, "tst", 0), "4222tst")
        );
    }
}