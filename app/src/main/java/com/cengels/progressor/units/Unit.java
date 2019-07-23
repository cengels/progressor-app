package com.cengels.progressor.units;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.cengels.progressor.ArrayExtensions;
import com.cengels.progressor.enums.ProgressType;

import java.util.HashMap;
import java.util.Map;

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
    public static final String HOURS = "h";
    public static final String MINUTES = "min";
    public static final String SECONDS = "sec";

    public static Map<String, Integer> conversionTable = new HashMap<>();

    static {
        conversionTable.put(Unit.KILOGRAMS, 1000);
        conversionTable.put(Unit.GRAMS, 1000);
        conversionTable.put(Unit.MILLILITERS, 1000);
        conversionTable.put(Unit.METERS, 1000);
        conversionTable.put(Unit.CENTIMETERS, 100);
        conversionTable.put(Unit.MILLIMETERS, 10);
        conversionTable.put(Unit.CALORIES, 1000);
    }

    @Nullable
    public static String getSmallestUnit(ProgressType progressType) {
        try {
            return ProgressType.class.getField(progressType.name()).getAnnotation(UnitDescriptor.class).value()[0];
        } catch (NoSuchFieldException exception) {
            return null;
        }
    }

    @Nullable
    public static String[] getUnits(ProgressType progressType) {
        try {
            return ProgressType.class.getField(progressType.name()).getAnnotation(UnitDescriptor.class).value();
        } catch (NoSuchFieldException exception) {
            return null;
        }
    }

    public static ProgressType getProgressType(@NonNull String unit) {
            for (ProgressType value : ProgressType.values()) {
                try {
                    if (ArrayExtensions.contains(ProgressType.class.getField(value.name()).getAnnotation(UnitDescriptor.class).value(), unit)) {
                        return value;
                    }
                } catch (NoSuchFieldException ignored) { }
            }

        return ProgressType.CUSTOM;

//        switch (unit) {
//            case NONE:
//                return ProgressType.COUNT;
//            case PERCENT:
//                return ProgressType.PERCENTAGE;
//            case TONS:
//            case KILOGRAMS:
//            case GRAMS:
//                return ProgressType.WEIGHT;
//            case LITERS:
//            case MILLILITERS:
//                return ProgressType.LIQUID;
//            case KILOMETERS:
//            case METERS:
//            case CENTIMETERS:
//            case MILLIMETERS:
//                // defaults to distance, not height
//                return ProgressType.DISTANCE;
//            case KILOCALORIES:
//            case CALORIES:
//                return ProgressType.CALORIES;
//            case YEARS:
//            case MONTHS:
//            case DAYS:
//                return ProgressType.DAYS;
//            case HOURS:
//            case MINUTES:
//            case SECONDS:
//                return ProgressType.TIME;
//            default:
//                return null;
//        }
    }
}
