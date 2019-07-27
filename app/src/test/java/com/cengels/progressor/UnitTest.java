package com.cengels.progressor;

import com.cengels.progressor.enums.ProgressType;
import com.cengels.progressor.units.Unit;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

// useful link: https://codelabs.developers.google.com/codelabs/android-testing/index.html

/**
 * Tests the {@link Unit} class.
 */
public class UnitTest {
    @ParameterizedTest
    @MethodSource("inferenceParameters")
    public void shouldInferProgressType(final String unit, final ProgressType progressType) {
        assertThat(Unit.getProgressType(unit)).as("ProgressType of %s", unit).isEqualTo(progressType);
    }


    @ParameterizedTest
    @MethodSource("getUnitsParameters")
    public void shouldGetUnits(final ProgressType progressType, final String[] units) {
        assertThat(Unit.getUnits(progressType)).as("Units for %s", progressType).isEqualTo(units);
    }

    @ParameterizedTest
    @MethodSource("getSmallestUnitParameters")
    public void shouldGetSmallestUnit(final ProgressType progressType, final String unit) {
        assertThat(Unit.getSmallestUnit(progressType)).as("Smallest unit for %s", progressType).isEqualTo(unit);
    }

    private static Stream inferenceParameters() {
        return Stream.of(
                Arguments.of(Unit.CALORIES, ProgressType.CALORIES),
                Arguments.of(Unit.KILOCALORIES, ProgressType.CALORIES),
                Arguments.of(Unit.CENTIMETERS, ProgressType.DISTANCE),
                Arguments.of(Unit.KILOMETERS, ProgressType.DISTANCE),
                Arguments.of(Unit.MILLIMETERS, ProgressType.DISTANCE),
                Arguments.of(Unit.METERS, ProgressType.DISTANCE),
                Arguments.of(Unit.GRAMS, ProgressType.WEIGHT),
                Arguments.of(Unit.KILOGRAMS, ProgressType.WEIGHT),
                Arguments.of(Unit.TONS, ProgressType.WEIGHT),
                Arguments.of(Unit.SECONDS, ProgressType.TIME),
                Arguments.of(Unit.MINUTES, ProgressType.TIME),
                Arguments.of(Unit.HOURS, ProgressType.TIME),
                Arguments.of(Unit.DAYS, ProgressType.TIME),
                Arguments.of(Unit.MONTHS, ProgressType.TIME),
                Arguments.of(Unit.YEARS, ProgressType.TIME),
                Arguments.of(Unit.NONE, ProgressType.COUNT),
                Arguments.of("test", ProgressType.CUSTOM),
                Arguments.of(Unit.PERCENT, ProgressType.PERCENTAGE),
                Arguments.of(Unit.MILLILITERS, ProgressType.LIQUID),
                Arguments.of(Unit.LITERS, ProgressType.LIQUID)
        );
    }

    private static Stream getUnitsParameters() {
        return Stream.of(
                Arguments.of(ProgressType.TIME, new String[] { Unit.SECONDS, Unit.MINUTES, Unit.HOURS, Unit.DAYS, Unit.WEEKS, Unit.MONTHS, Unit.YEARS }),
                Arguments.of(ProgressType.CALORIES, new String[] { Unit.CALORIES, Unit.KILOCALORIES }),
                Arguments.of(ProgressType.LIQUID, new String[] { Unit.MILLILITERS, Unit.LITERS }),
                Arguments.of(ProgressType.HEIGHT, new String[] { Unit.MILLIMETERS, Unit.CENTIMETERS, Unit.METERS, Unit.KILOMETERS }),
                Arguments.of(ProgressType.DISTANCE, new String[] { Unit.MILLIMETERS, Unit.CENTIMETERS, Unit.METERS, Unit.KILOMETERS }),
                Arguments.of(ProgressType.WEIGHT, new String[] { Unit.GRAMS, Unit.KILOGRAMS, Unit.TONS }),
                Arguments.of(ProgressType.PERCENTAGE, new String[] { Unit.PERCENT }),
                Arguments.of(ProgressType.COUNT, new String[] { Unit.NONE }),
                Arguments.of(ProgressType.CUSTOM, null)
        );
    }

    private static Stream getSmallestUnitParameters() {
        return Stream.of(
                Arguments.of(ProgressType.TIME, Unit.SECONDS),
                Arguments.of(ProgressType.CALORIES, Unit.CALORIES),
                Arguments.of(ProgressType.LIQUID, Unit.MILLILITERS),
                Arguments.of(ProgressType.HEIGHT, Unit.MILLIMETERS),
                Arguments.of(ProgressType.DISTANCE, Unit.MILLIMETERS),
                Arguments.of(ProgressType.WEIGHT, Unit.GRAMS),
                Arguments.of(ProgressType.PERCENTAGE, Unit.PERCENT),
                Arguments.of(ProgressType.COUNT, Unit.NONE),
                Arguments.of(ProgressType.CUSTOM, null)
        );
    }
}