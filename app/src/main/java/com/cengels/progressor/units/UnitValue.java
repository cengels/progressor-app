package com.cengels.progressor.units;

import androidx.annotation.NonNull;
import com.cengels.progressor.enums.ProgressType;

import java.io.Serializable;

public class UnitValue implements Serializable {
    private double value;
    @NonNull
    private final ProgressType type;
    private int decimals;
    private String unit;

    public UnitValue(@NonNull ProgressType type) {
        this.type = type;
        this.unit = Unit.getSmallestUnit(this.type);

        this.inferDecimals();
    }

    public UnitValue(double value, @NonNull ProgressType type) {
        this(type);
        this.value = value;
    }

    public UnitValue(@NonNull String unit, int decimals) {
        this.type = ProgressType.CUSTOM;
        this.unit = unit;
        this.decimals = decimals;
    }

    public UnitValue(double value, @NonNull String unit) {
        this(unit, 2);
        this.value = value;
    }

    public UnitValue(int value, @NonNull String unit) {
        this(unit, 0);
        this.value = value;
    }

    public void set(double value) {
        this.value = value;
    }

    public double get() {
        return this.value;
    }

    @NonNull
    public ProgressType getType() {
        return this.type;
    }

    public int getDecimals() {
        return this.decimals;
    }

    public String getUnit() {
        return this.unit;
    }

    public String getBestUnit() {
        if (this.type == ProgressType.CUSTOM) {
            return this.unit;
        }

        return Convert.from(this.getUnit()).value(this.value).bestUnit();
    }

    public double getBest() {
        if (this.type == ProgressType.CUSTOM) {
            return this.value;
        }

        return Convert.from(this.getUnit()).value(this.value).best();
    }

    private void inferDecimals() {
        switch (this.type) {
            case COUNT:
            case DAYS:
            case TIME:
                this.decimals = 0;
                break;
            case PERCENTAGE:
                this.decimals = 1;
                break;
            default:
                this.decimals = 2;
                break;
        }
    }

    public String getFormattedValue() {
        double value = this.getBest();

        if (this.decimals == 0) {
            return String.valueOf((long)value);
        } else {
            return String.format("%." + this.decimals + "f", value);
        }
    }

    public String getFormattedValue(@NonNull String unit) {
        double value = Convert.from(this.unit).value(this.value).to(unit);

        if (this.decimals == 0) {
            return String.valueOf((long)value);
        } else {
            return String.format("%." + this.decimals + "f", value);
        }
    }

    @Override
    public String toString() {
        return this.getFormattedValue() + " " + this.unit;
    }

    public String toString(@NonNull String unit) {
        return this.getFormattedValue(unit) + " " + this.unit;
    }
}
