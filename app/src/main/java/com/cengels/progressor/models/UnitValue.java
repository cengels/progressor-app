package com.cengels.progressor.models;

import androidx.annotation.NonNull;
import com.cengels.progressor.ArrayExtensions;
import com.cengels.progressor.enums.ProgressType;
import com.cengels.progressor.enums.Unit;

import java.io.Serializable;

public class UnitValue implements Serializable {
    private double value;
    @NonNull
    private ProgressType type;
    private String unitSuffix;
    private int decimals;

    public UnitValue(double value, @NonNull ProgressType type, @NonNull String unitSuffix, int decimals) {
        this.value = value;
        this.type = type;
        this.decimals = decimals;
        this.unitSuffix = unitSuffix;

        this.correctUnit();
    }

    public UnitValue(double value, @NonNull String unitSuffix, int decimals) {
        this(value, ProgressType.CUSTOM, unitSuffix, decimals);

        // If the unitSuffix corresponds to any of the predefined ones, reassign type accordingly.
        ProgressType progressType = Unit.getProgressType(this.unitSuffix);

        if (progressType != null) {
            this.type = progressType;
        }
    }

    public UnitValue(int value, @NonNull ProgressType type, @NonNull String unitSuffix) {
        this(value, type, unitSuffix, 0);
    }

    public UnitValue(int value, @NonNull String unitSuffix) {
        this(value, unitSuffix, 0);
    }

    public UnitValue(int value) {
        this(value, ProgressType.COUNT, "");
    }

    public void set(double value) {
        this.value = value;

        this.correctUnit();
    }

    public void set(double value, String unitSuffix) {
        this.set(value);
        this.unitSuffix = unitSuffix;
    }

    public double get() {
        return this.value;
    }

    @NonNull
    public ProgressType getType() {
        return this.type;
    }

    public String getUnitSuffix() {
        return this.unitSuffix;
    }

    public int getDecimals() {
        return this.decimals;
    }

    /**
     * Checks whether the value can be shortened by conversion to a higher/lower unit and converts it if possible.
     */
    private void correctUnit() {
        switch (this.type) {
            case COUNT:
                this.unitSuffix = Unit.NONE;
                break;
            case WEIGHT:
                this.convert(Unit.TONS, Unit.KILOGRAMS, Unit.GRAMS);
                break;
            case LIQUID:
                this.convert(Unit.LITERS, Unit.MILLILITERS);
                break;
            case DISTANCE:
                this.convert(Unit.KILOMETERS, Unit.METERS, Unit.CENTIMETERS, Unit.MILLILITERS);
                break;
            case CALORIES:
                this.convert(Unit.KILOCALORIES, Unit.CALORIES);
                break;
            case CUSTOM:
            default:
        }
    }

    private void convert(String... units) {
        int index = ArrayExtensions.indexOf(units, this.unitSuffix);

        if (index == -1) {
            throw new IllegalArgumentException("The passed units array does not contain this object's unitSuffix.");
        }

        while ((index < units.length - 1 && this.value < 1)
                || (index > 0 && this.value >= Unit.conversionTable.get(units[index - 1]))) {
            if (this.value < 1) {
                index++;
                this.value *= Unit.conversionTable.get(units[index - 1]);
                this.unitSuffix = units[index];
            } else {
                index--;
                this.value /= Unit.conversionTable.get(units[index]);
                this.unitSuffix = units[index];
            }
        }
    }

    @Override
    public String toString() {
        if (this.decimals == 0) {
            return (long)this.value + " " + this.unitSuffix;
        } else {
            return String.format("%." + this.decimals + "f", this.value) + " " + this.unitSuffix;
        }
    }
}
