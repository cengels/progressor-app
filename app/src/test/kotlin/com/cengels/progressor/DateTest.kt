package com.cengels.progressor

import com.cengels.progressor.extensions.roundToHour
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.*
import java.util.stream.Stream

/** Tests various date extensions. */
class DateTest {
    @ParameterizedTest
    @MethodSource("roundingParameters")
    fun shouldRoundToHour(source: Calendar, expected: Calendar) {
        val actual = source.apply { roundToHour() }
        assertThat(actual).isEqualTo(expected)
    }

    companion object {
        @JvmStatic
        private fun roundingParameters(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    Calendar.getInstance().apply { set(2018, 11, 20, 5, 20) },
                    Calendar.getInstance().apply {
                        set(2018, 11, 20, 5, 0, 0)
                        set(Calendar.MILLISECOND, 0)
                    }
                ),
                Arguments.of(
                    Calendar.getInstance().apply { set(2018, 11, 20, 5, 0) },
                    Calendar.getInstance().apply {
                        set(2018, 11, 20, 5, 0, 0)
                        set(Calendar.MILLISECOND, 0)
                    }
                ),
                Arguments.of(
                    Calendar.getInstance().apply { set(2018, 11, 20, 5, 40) },
                    Calendar.getInstance().apply {
                        set(2018, 11, 20, 6, 0, 0)
                        set(Calendar.MILLISECOND, 0)
                    }
                ),
                Arguments.of(
                    Calendar.getInstance().apply { set(2018, 11, 20, 5, 30, 0) },
                    Calendar.getInstance().apply {
                        set(2018, 11, 20, 6, 0, 0)
                        set(Calendar.MILLISECOND, 0)
                    }
                ),
                Arguments.of(
                    Calendar.getInstance().apply { set(2018, 11, 20, 23, 40) },
                    Calendar.getInstance().apply {
                        set(2018, 11, 21, 0, 0, 0)
                        set(Calendar.MILLISECOND, 0)
                    }
                )
            )
        }
    }
}
