package com.cengels.progressor.units;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.cengels.progressor.ArrayExtensions;
import com.cengels.progressor.enums.ProgressType;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public abstract class Unit {
    public static final String NONE = "";
    public static final String PERCENT = "%";
    public static final String TONS = "t";
    public static final String KILOGRAMS = "kg";
    public static final String GRAMS = "g";
    public static final String LITERS = "l";
    public static final String MILLILITERS = "ml";
    public static final String KILOMETERS = "km";
    public static final String METERS = "m";
    public static final String CENTIMETERS = "cm";
    public static final String MILLIMETERS = "mm";
    public static final String KILOCALORIES = "kcal";
    public static final String CALORIES = "cal";
    public static final String YEARS = "years";
    public static final String MONTHS = "months";
    public static final String DAYS = "days";
    public static final String WEEKS = "wks";
    public static final String HOURS = "h";
    public static final String MINUTES = "min";
    public static final String SECONDS = "s";

    public static Map<String, Double> conversionTable = new HashMap<>();

    static {
        conversionTable.put(Unit.KILOGRAMS, 1000.0);
        conversionTable.put(Unit.GRAMS, 1000.0);
        conversionTable.put(Unit.MILLILITERS, 1000.0);
        conversionTable.put(Unit.METERS, 1000.0);
        conversionTable.put(Unit.CENTIMETERS, 100.0);
        conversionTable.put(Unit.MILLIMETERS, 10.0);
        conversionTable.put(Unit.CALORIES, 1000.0);
        conversionTable.put(Unit.MONTHS, 12.0);
        conversionTable.put(Unit.WEEKS, 4.34523809524); // average number of days/month divided by days/week
        conversionTable.put(Unit.DAYS, 7.0);
        conversionTable.put(Unit.HOURS, 24.0);
        conversionTable.put(Unit.MINUTES, 60.0);
        conversionTable.put(Unit.SECONDS, 60.0);
    }

    @Nullable
    public static String getSmallestUnit(ProgressType progressType) {
        try {
            return Objects.requireNonNull(ProgressType.class.getField(progressType.name()).getAnnotation(UnitDescriptor.class)).value()[0];
        } catch (Exception exception) {
            return null;
        }
    }

    @Nullable
    public static String[] getUnits(ProgressType progressType) {
        try {
            return Objects.requireNonNull(ProgressType.class.getField(progressType.name()).getAnnotation(UnitDescriptor.class)).value();
        } catch (Exception exception) {
            return null;
        }
    }

    public static ProgressType getProgressType(@NonNull String unit) {
            for (ProgressType value : ProgressType.values()) {
                try {
                    if (ArrayExtensions.contains(Objects.requireNonNull(ProgressType.class.getField(value.name()).getAnnotation(UnitDescriptor.class)).value(), unit)) {
                        return value;
                    }
                } catch (Exception ignored) { }
            }

        return ProgressType.CUSTOM;
    }
}
