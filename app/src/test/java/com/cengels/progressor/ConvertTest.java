package com.cengels.progressor;

import com.cengels.progressor.exceptions.IllegalConversionException;
import com.cengels.progressor.units.Convert;
import com.cengels.progressor.units.Unit;
import com.cengels.progressor.units.UnitValue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;

// useful link: https://codelabs.developers.google.com/codelabs/android-testing/index.html

/**
 * Tests the {@link Convert} class.
 */
public class ConvertTest {
    @Test
    public void constructorOverloadsShouldEqualResult() {
        final Convert convert1 = Convert.from(500.2, Unit.KILOGRAMS);
        final Convert convert2 = Convert.from(new UnitValue(500.2, Unit.KILOGRAMS));

        assertThat(convert1.getValue()).isEqualTo(convert2.getValue());
    }

    @Test
    public void standardConversion_sameUnitShouldReturnInput() {
        assertThat(Convert.from(500, Unit.KILOGRAMS).to(Unit.KILOGRAMS).get()).isEqualTo(500.0);
        assertThat(Convert.from(20, Unit.LITERS).to(Unit.LITERS).get()).isEqualTo(20.0);
        assertThat(Convert.from(5, Unit.NONE).to(Unit.NONE).get()).isEqualTo(5.0);
    }

    @ParameterizedTest
    @MethodSource("smallerToLargerParameters")
    public void standardConversion_largerUnitShouldReturnSmallerValue(final UnitValue source, final UnitValue target) {
        double actual = Convert.from(source).to(target.getUnit()).get();
        double expected = target.get();
        assertEquals(expected, actual, 0.0000005, source.getUnit() + " to " + target.getUnit());
    }

    @ParameterizedTest
    @MethodSource("largerToSmallerParameters")
    public void standardConversion_smallerUnitShouldReturnLargerValue(final UnitValue source, final UnitValue target) {
        double actual = Convert.from(source).to(target.getUnit()).get();
        double expected = target.get();
        assertEquals(expected, actual, 0.0000005, source.getUnit() + " to " + target.getUnit());
    }

    @ParameterizedTest
    @MethodSource("bestParametersSmallToLarge")
    public void bestConversion_shouldGetLastUnitWithValueOver1(final UnitValue source, final UnitValue target) {
        final String actual = Convert.from(source).best().getUnit();
        assertThat(actual).as("%s to %s", source.getUnit(), target.getUnit()).isEqualTo(target.getUnit());
    }

    @ParameterizedTest
    @MethodSource("bestParametersSmallToLarge")
    public void bestConversion_shouldGetLastUnitValueWithValueOver1(final UnitValue source, final UnitValue target) {
        double actual = Convert.from(source).best().get();
        double expected = target.get();
        assertEquals(expected, actual, 0.0000005, source.getUnit() + " to " + target.getUnit());
    }

    @ParameterizedTest
    @MethodSource("bestParametersLargeToSmall")
    public void bestConversion_shouldGetFirstUnitWithValueOver1(final UnitValue source, final UnitValue target) {
        final String actual = Convert.from(source).best().getUnit();
        assertThat(actual).as("%s to %s", source.getUnit(), target.getUnit()).isEqualTo(target.getUnit());
    }

    @ParameterizedTest
    @MethodSource("bestParametersLargeToSmall")
    public void bestConversion_shouldGetFirstUnitValueWithValueOver1(final UnitValue source, final UnitValue target) {
        double actual = Convert.from(source).best().get();
        double expected = target.get();
        assertEquals(expected, actual, 0.0000005, source.getUnit() + " to " + target.getUnit());
    }

    @Test
    public void bestConversion_invalidUnitShouldReturnInput() {
        assertThat(Convert.from(new UnitValue(500.0, Unit.NONE)).best().getUnit()).isEmpty();
        assertThat(Convert.from(new UnitValue(500.0, "test")).best().getUnit()).isEqualTo("test");
    }

    @Test
    public void throwOnInvalidConversion() {
        assertThrows(IllegalConversionException.class, () -> Convert.from(500.0, Unit.DAYS).to(Unit.GRAMS), "From " + Unit.DAYS + " to " + Unit.GRAMS);
        assertThrows(IllegalConversionException.class, () -> Convert.from(500.0, Unit.NONE).to(Unit.CENTIMETERS), "From " + Unit.NONE + " to " + Unit.CENTIMETERS);
        assertThrows(IllegalConversionException.class, () -> Convert.from(500.0, "test").to(Unit.CENTIMETERS), "From test to " + Unit.CENTIMETERS);
    }

    private static Stream smallerToLargerParameters() {
        return Stream.of(
            Arguments.of(new UnitValue(5000.0, Unit.GRAMS), new UnitValue(5.0, Unit.KILOGRAMS)),
            Arguments.of(new UnitValue(5000.0, Unit.GRAMS), new UnitValue(0.005, Unit.TONS)),
            Arguments.of(new UnitValue(5000.0, Unit.KILOGRAMS), new UnitValue(5.0, Unit.TONS)),
            Arguments.of(new UnitValue(5000000.0, Unit.MILLIMETERS), new UnitValue(500000.0, Unit.CENTIMETERS)),
            Arguments.of(new UnitValue(5000000.0, Unit.MILLIMETERS), new UnitValue(5000.0, Unit.METERS)),
            Arguments.of(new UnitValue(5000000.0, Unit.MILLIMETERS), new UnitValue(5.0, Unit.KILOMETERS)),
            Arguments.of(new UnitValue(500.0, Unit.METERS), new UnitValue(0.5, Unit.KILOMETERS)),
            Arguments.of(new UnitValue(200.0, Unit.MILLILITERS), new UnitValue(0.2, Unit.LITERS)),
            Arguments.of(new UnitValue(50.0, Unit.CALORIES), new UnitValue(0.05, Unit.KILOCALORIES)),
            Arguments.of(new UnitValue(180.0, Unit.SECONDS), new UnitValue(3.0, Unit.MINUTES)),
            Arguments.of(new UnitValue(3600.0, Unit.SECONDS), new UnitValue(1.0, Unit.HOURS)),
            Arguments.of(new UnitValue(259200.0, Unit.SECONDS), new UnitValue(3.0, Unit.DAYS)),
            Arguments.of(new UnitValue(12.0, Unit.DAYS), new UnitValue(1.71428571429, Unit.WEEKS)),
            Arguments.of(new UnitValue(250.0, Unit.DAYS), new UnitValue(8.21917808218, Unit.MONTHS)),
            Arguments.of(new UnitValue(500.0, Unit.DAYS), new UnitValue(1.3698630137, Unit.YEARS))
        );
    }

    private static Stream largerToSmallerParameters() {
        return Stream.of(
                Arguments.of(new UnitValue(5.0, Unit.KILOGRAMS), new UnitValue(5000.0, Unit.GRAMS)),
                Arguments.of(new UnitValue(0.005, Unit.TONS), new UnitValue(5000.0, Unit.GRAMS)),
                Arguments.of(new UnitValue(5.0, Unit.TONS), new UnitValue(5000.0, Unit.KILOGRAMS)),
                Arguments.of(new UnitValue(500000.0, Unit.CENTIMETERS), new UnitValue(5000000.0, Unit.MILLIMETERS)),
                Arguments.of(new UnitValue(5000.0, Unit.METERS), new UnitValue(5000000.0, Unit.MILLIMETERS)),
                Arguments.of(new UnitValue(5.0, Unit.KILOMETERS), new UnitValue(5000000.0, Unit.MILLIMETERS)),
                Arguments.of(new UnitValue(0.5, Unit.KILOMETERS), new UnitValue(500.0, Unit.METERS)),
                Arguments.of(new UnitValue(0.2, Unit.LITERS), new UnitValue(200.0, Unit.MILLILITERS)),
                Arguments.of(new UnitValue(0.05, Unit.KILOCALORIES), new UnitValue(50.0, Unit.CALORIES)),
                Arguments.of(new UnitValue(3.0, Unit.MINUTES), new UnitValue(180.0, Unit.SECONDS)),
                Arguments.of(new UnitValue(1.0, Unit.HOURS), new UnitValue(3600.0, Unit.SECONDS)),
                Arguments.of(new UnitValue(3.0, Unit.DAYS), new UnitValue(259200.0, Unit.SECONDS)),
                Arguments.of(new UnitValue(1.71428571429, Unit.WEEKS), new UnitValue(12.0, Unit.DAYS)),
                Arguments.of(new UnitValue(8.21917808218, Unit.MONTHS), new UnitValue(250.0, Unit.DAYS)),
                Arguments.of(new UnitValue(1.3698630137, Unit.YEARS), new UnitValue(500.0, Unit.DAYS))
        );
    }

    private static Stream bestParametersSmallToLarge() {
        return Stream.of(
                Arguments.of(new UnitValue(5000.0, Unit.GRAMS), new UnitValue(5.0, Unit.KILOGRAMS)),
                Arguments.of(new UnitValue(50.0, Unit.GRAMS), new UnitValue(50.0, Unit.GRAMS)),
                Arguments.of(new UnitValue(2000000.0, Unit.GRAMS), new UnitValue(2.0, Unit.TONS)),
                Arguments.of(new UnitValue(2500000.0, Unit.GRAMS), new UnitValue(2.5, Unit.TONS)),
                Arguments.of(new UnitValue(30.0, Unit.GRAMS), new UnitValue(30.0, Unit.GRAMS)),
                Arguments.of(new UnitValue(5000000.0, Unit.MILLIMETERS), new UnitValue(5.0, Unit.KILOMETERS)),
                Arguments.of(new UnitValue(5000.0, Unit.METERS), new UnitValue(5.0, Unit.KILOMETERS)),
                Arguments.of(new UnitValue(5000.0, Unit.CENTIMETERS), new UnitValue(50.0, Unit.METERS)),
                Arguments.of(new UnitValue(2000.0, Unit.MILLILITERS), new UnitValue(2.0, Unit.LITERS)),
                Arguments.of(new UnitValue(20.0, Unit.MILLILITERS), new UnitValue(20.0, Unit.MILLILITERS)),
                Arguments.of(new UnitValue(5000.0, Unit.CALORIES), new UnitValue(5.0, Unit.KILOCALORIES)),
                Arguments.of(new UnitValue(180.0, Unit.SECONDS), new UnitValue(3.0, Unit.MINUTES)),
                Arguments.of(new UnitValue(3600.0, Unit.SECONDS), new UnitValue(1.0, Unit.HOURS)),
                Arguments.of(new UnitValue(259200.0, Unit.SECONDS), new UnitValue(3.0, Unit.DAYS)),
                Arguments.of(new UnitValue(12.0, Unit.DAYS), new UnitValue(1.71428571429, Unit.WEEKS)),
                Arguments.of(new UnitValue(250.0, Unit.DAYS), new UnitValue(8.21917808218, Unit.MONTHS)),
                Arguments.of(new UnitValue(500.0, Unit.DAYS), new UnitValue(1.3698630137, Unit.YEARS)),
                Arguments.of(new UnitValue(5.0, Unit.DAYS), new UnitValue(5.0, Unit.DAYS))
        );
    }

    private static Stream bestParametersLargeToSmall() {
        return Stream.of(
                Arguments.of(new UnitValue(0.5, Unit.KILOGRAMS), new UnitValue(500.0, Unit.GRAMS)),
                Arguments.of(new UnitValue(0.2, Unit.TONS), new UnitValue(200.0, Unit.KILOGRAMS)),
                Arguments.of(new UnitValue(0.000005, Unit.KILOMETERS), new UnitValue(5.0, Unit.MILLIMETERS)),
                Arguments.of(new UnitValue(0.05, Unit.KILOCALORIES), new UnitValue(50.0, Unit.CALORIES)),
                Arguments.of(new UnitValue(0.00006236, Unit.DAYS), new UnitValue(5.387904, Unit.SECONDS))
        );
    }
}