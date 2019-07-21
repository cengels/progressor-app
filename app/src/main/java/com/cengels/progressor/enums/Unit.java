package com.cengels.progressor.enums;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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

    public static Map<String, Integer> conversionTable = new HashMap<String, Integer>();

    static {
        conversionTable.put(Unit.TONS, 1000);
        conversionTable.put(Unit.KILOGRAMS, 1000);
        conversionTable.put(Unit.LITERS, 1000);
        conversionTable.put(Unit.KILOMETERS, 1000);
        conversionTable.put(Unit.METERS, 100);
        conversionTable.put(Unit.CENTIMETERS, 10);
        conversionTable.put(Unit.KILOCALORIES, 1000);
    }

    @Nullable
    public static ProgressType getProgressType(@NonNull String unit) {
        switch (unit) {
            case NONE:
                return ProgressType.COUNT;
            case PERCENT:
                return ProgressType.PERCENTAGE;
            case TONS:
            case KILOGRAMS:
            case GRAMS:
                return ProgressType.WEIGHT;
            case LITERS:
            case MILLILITERS:
                return ProgressType.LIQUID;
            case KILOMETERS:
            case METERS:
            case CENTIMETERS:
            case MILLIMETERS:
                // defaults to distance, not height
                return ProgressType.DISTANCE;
            case KILOCALORIES:
            case CALORIES:
                return ProgressType.CALORIES;
            case YEARS:
            case MONTHS:
            case DAYS:
                return ProgressType.DAYS;
            case HOURS:
            case MINUTES:
            case SECONDS:
                return ProgressType.TIME;
            default:
                return null;
        }
    }
}
