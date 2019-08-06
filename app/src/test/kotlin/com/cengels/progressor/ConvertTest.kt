package com.cengels.progressor

import com.cengels.progressor.exceptions.IllegalConversionException
import com.cengels.progressor.units.Convert
import com.cengels.progressor.units.Unit
import com.cengels.progressor.units.UnitValue
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

import java.util.stream.Stream

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows

// useful link: https://codelabs.developers.google.com/codelabs/android-testing/index.html

/**
 * Tests the [Convert] class.
 */
class ConvertTest {
    @Test
    fun constructorOverloadsShouldEqualResult() {
        val convert1 = Convert.from(500.2, Unit.KILOGRAMS)
        val convert2 = Convert.from(UnitValue(500.2, Unit.KILOGRAMS))

        assertThat(convert1.value).isEqualTo(convert2.value)
    }

    @Test
    fun standardConversion_sameUnitShouldReturnInput() {
        assertThat(Convert.from(500.0, Unit.KILOGRAMS).to(Unit.KILOGRAMS).get()).isEqualTo(500.0)
        assertThat(Convert.from(20.0, Unit.LITERS).to(Unit.LITERS).get()).isEqualTo(20.0)
        assertThat(Convert.from(5.0, Unit.NONE).to(Unit.NONE).get()).isEqualTo(5.0)
    }

    @ParameterizedTest
    @MethodSource("smallerToLargerParameters")
    fun standardConversion_largerUnitShouldReturnSmallerValue(source: UnitValue, target: UnitValue) {
        val actual = Convert.from(source).to(target.unit).get()
        val expected = target.get()
        assertEquals(expected, actual, 0.0000005, source.unit + " to " + target.unit)
    }

    @ParameterizedTest
    @MethodSource("largerToSmallerParameters")
    fun standardConversion_smallerUnitShouldReturnLargerValue(source: UnitValue, target: UnitValue) {
        val actual = Convert.from(source).to(target.unit).get()
        val expected = target.get()
        assertEquals(expected, actual, 0.0000005, source.unit + " to " + target.unit)
    }

    @ParameterizedTest
    @MethodSource("bestParametersSmallToLarge")
    fun bestConversion_shouldGetLastUnitWithValueOver1(source: UnitValue, target: UnitValue) {
        val actual = Convert.from(source).best().unit
        assertThat(actual).`as`("%s to %s", source.unit, target.unit).isEqualTo(target.unit)
    }

    @ParameterizedTest
    @MethodSource("bestParametersSmallToLarge")
    fun bestConversion_shouldGetLastUnitValueWithValueOver1(source: UnitValue, target: UnitValue) {
        val actual = Convert.from(source).best().get()
        val expected = target.get()
        assertEquals(expected, actual, 0.0000005, source.unit + " to " + target.unit)
    }

    @ParameterizedTest
    @MethodSource("bestParametersLargeToSmall")
    fun bestConversion_shouldGetFirstUnitWithValueOver1(source: UnitValue, target: UnitValue) {
        val actual = Convert.from(source).best().unit
        assertThat(actual).`as`("%s to %s", source.unit, target.unit).isEqualTo(target.unit)
    }

    @ParameterizedTest
    @MethodSource("bestParametersLargeToSmall")
    fun bestConversion_shouldGetFirstUnitValueWithValueOver1(source: UnitValue, target: UnitValue) {
        val actual = Convert.from(source).best().get()
        val expected = target.get()
        assertEquals(expected, actual, 0.0000005, source.unit + " to " + target.unit)
    }

    @Test
    fun bestConversion_invalidUnitShouldReturnInput() {
        assertThat(Convert.from(UnitValue(500.0, Unit.NONE)).best().unit).isEmpty()
        assertThat(Convert.from(UnitValue(500.0, "test")).best().unit).isEqualTo("test")
    }

    @Test
    fun throwOnInvalidConversion() {
        assertThrows(IllegalConversionException::class.java, { Convert.from(500.0, Unit.DAYS).to(Unit.GRAMS) }, "From ${Unit.DAYS} to ${Unit.GRAMS}")
        assertThrows(IllegalConversionException::class.java, { Convert.from(500.0, Unit.NONE).to(Unit.CENTIMETERS) }, "From ${Unit.NONE} to ${Unit.CENTIMETERS}")
        assertThrows(IllegalConversionException::class.java, { Convert.from(500.0, "test").to(Unit.CENTIMETERS) }, "From test to ${Unit.CENTIMETERS}")
    }

    companion object {
        @JvmStatic
        private fun smallerToLargerParameters(): Stream<Arguments> {
            return Stream.of(
                    Arguments.of(UnitValue(5000.0, Unit.GRAMS), UnitValue(5.0, Unit.KILOGRAMS)),
                    Arguments.of(UnitValue(5000.0, Unit.GRAMS), UnitValue(0.005, Unit.TONS)),
                    Arguments.of(UnitValue(5000.0, Unit.KILOGRAMS), UnitValue(5.0, Unit.TONS)),
                    Arguments.of(UnitValue(5000000.0, Unit.MILLIMETERS), UnitValue(500000.0, Unit.CENTIMETERS)),
                    Arguments.of(UnitValue(5000000.0, Unit.MILLIMETERS), UnitValue(5000.0, Unit.METERS)),
                    Arguments.of(UnitValue(5000000.0, Unit.MILLIMETERS), UnitValue(5.0, Unit.KILOMETERS)),
                    Arguments.of(UnitValue(500.0, Unit.METERS), UnitValue(0.5, Unit.KILOMETERS)),
                    Arguments.of(UnitValue(200.0, Unit.MILLILITERS), UnitValue(0.2, Unit.LITERS)),
                    Arguments.of(UnitValue(50.0, Unit.CALORIES), UnitValue(0.05, Unit.KILOCALORIES)),
                    Arguments.of(UnitValue(180.0, Unit.SECONDS), UnitValue(3.0, Unit.MINUTES)),
                    Arguments.of(UnitValue(3600.0, Unit.SECONDS), UnitValue(1.0, Unit.HOURS)),
                    Arguments.of(UnitValue(259200.0, Unit.SECONDS), UnitValue(3.0, Unit.DAYS)),
                    Arguments.of(UnitValue(12.0, Unit.DAYS), UnitValue(1.71428571429, Unit.WEEKS)),
                    Arguments.of(UnitValue(250.0, Unit.DAYS), UnitValue(8.21917808218, Unit.MONTHS)),
                    Arguments.of(UnitValue(500.0, Unit.DAYS), UnitValue(1.3698630137, Unit.YEARS))
            )
        }

        @JvmStatic
        private fun largerToSmallerParameters(): Stream<Arguments> {
            return Stream.of(
                    Arguments.of(UnitValue(5.0, Unit.KILOGRAMS), UnitValue(5000.0, Unit.GRAMS)),
                    Arguments.of(UnitValue(0.005, Unit.TONS), UnitValue(5000.0, Unit.GRAMS)),
                    Arguments.of(UnitValue(5.0, Unit.TONS), UnitValue(5000.0, Unit.KILOGRAMS)),
                    Arguments.of(UnitValue(500000.0, Unit.CENTIMETERS), UnitValue(5000000.0, Unit.MILLIMETERS)),
                    Arguments.of(UnitValue(5000.0, Unit.METERS), UnitValue(5000000.0, Unit.MILLIMETERS)),
                    Arguments.of(UnitValue(5.0, Unit.KILOMETERS), UnitValue(5000000.0, Unit.MILLIMETERS)),
                    Arguments.of(UnitValue(0.5, Unit.KILOMETERS), UnitValue(500.0, Unit.METERS)),
                    Arguments.of(UnitValue(0.2, Unit.LITERS), UnitValue(200.0, Unit.MILLILITERS)),
                    Arguments.of(UnitValue(0.05, Unit.KILOCALORIES), UnitValue(50.0, Unit.CALORIES)),
                    Arguments.of(UnitValue(3.0, Unit.MINUTES), UnitValue(180.0, Unit.SECONDS)),
                    Arguments.of(UnitValue(1.0, Unit.HOURS), UnitValue(3600.0, Unit.SECONDS)),
                    Arguments.of(UnitValue(3.0, Unit.DAYS), UnitValue(259200.0, Unit.SECONDS)),
                    Arguments.of(UnitValue(1.71428571429, Unit.WEEKS), UnitValue(12.0, Unit.DAYS)),
                    Arguments.of(UnitValue(8.21917808218, Unit.MONTHS), UnitValue(250.0, Unit.DAYS)),
                    Arguments.of(UnitValue(1.3698630137, Unit.YEARS), UnitValue(500.0, Unit.DAYS))
            )
        }

        @JvmStatic
        private fun bestParametersSmallToLarge(): Stream<Arguments> {
            return Stream.of(
                    Arguments.of(UnitValue(5000.0, Unit.GRAMS), UnitValue(5.0, Unit.KILOGRAMS)),
                    Arguments.of(UnitValue(50.0, Unit.GRAMS), UnitValue(50.0, Unit.GRAMS)),
                    Arguments.of(UnitValue(2000000.0, Unit.GRAMS), UnitValue(2.0, Unit.TONS)),
                    Arguments.of(UnitValue(2500000.0, Unit.GRAMS), UnitValue(2.5, Unit.TONS)),
                    Arguments.of(UnitValue(30.0, Unit.GRAMS), UnitValue(30.0, Unit.GRAMS)),
                    Arguments.of(UnitValue(5000000.0, Unit.MILLIMETERS), UnitValue(5.0, Unit.KILOMETERS)),
                    Arguments.of(UnitValue(5000.0, Unit.METERS), UnitValue(5.0, Unit.KILOMETERS)),
                    Arguments.of(UnitValue(5000.0, Unit.CENTIMETERS), UnitValue(50.0, Unit.METERS)),
                    Arguments.of(UnitValue(2000.0, Unit.MILLILITERS), UnitValue(2.0, Unit.LITERS)),
                    Arguments.of(UnitValue(20.0, Unit.MILLILITERS), UnitValue(20.0, Unit.MILLILITERS)),
                    Arguments.of(UnitValue(5000.0, Unit.CALORIES), UnitValue(5.0, Unit.KILOCALORIES)),
                    Arguments.of(UnitValue(180.0, Unit.SECONDS), UnitValue(3.0, Unit.MINUTES)),
                    Arguments.of(UnitValue(3600.0, Unit.SECONDS), UnitValue(1.0, Unit.HOURS)),
                    Arguments.of(UnitValue(259200.0, Unit.SECONDS), UnitValue(3.0, Unit.DAYS)),
                    Arguments.of(UnitValue(12.0, Unit.DAYS), UnitValue(1.71428571429, Unit.WEEKS)),
                    Arguments.of(UnitValue(250.0, Unit.DAYS), UnitValue(8.21917808218, Unit.MONTHS)),
                    Arguments.of(UnitValue(500.0, Unit.DAYS), UnitValue(1.3698630137, Unit.YEARS)),
                    Arguments.of(UnitValue(5.0, Unit.DAYS), UnitValue(5.0, Unit.DAYS))
            )
        }

        @JvmStatic
        private fun bestParametersLargeToSmall(): Stream<Arguments> {
            return Stream.of(
                    Arguments.of(UnitValue(0.5, Unit.KILOGRAMS), UnitValue(500.0, Unit.GRAMS)),
                    Arguments.of(UnitValue(0.2, Unit.TONS), UnitValue(200.0, Unit.KILOGRAMS)),
                    Arguments.of(UnitValue(0.000005, Unit.KILOMETERS), UnitValue(5.0, Unit.MILLIMETERS)),
                    Arguments.of(UnitValue(0.05, Unit.KILOCALORIES), UnitValue(50.0, Unit.CALORIES)),
                    Arguments.of(UnitValue(0.00006236, Unit.DAYS), UnitValue(5.387904, Unit.SECONDS))
            )
        }
    }
}