package com.cengels.progressor.units;

import android.annotation.SuppressLint;
import androidx.annotation.NonNull;
import com.cengels.progressor.enums.ProgressType;

import java.io.Serializable;
import java.util.Objects;

public class UnitValue implements Serializable {
    public static final int SECS_PER_MINUTE = 60;
    private double value;
    @NonNull
    private final ProgressType type;
    private int decimals = -1;
    private String unit;

    public UnitValue(@NonNull final ProgressType type) {
        this.type = type;
        this.unit = Unit.getSmallestUnit(this.type);

        if (this.unit == null) {
            this.unit = "";
        }

        this.inferDecimals();
    }

    public UnitValue(final double value, @NonNull final ProgressType type) {
        this(type);
        this.value = value;
    }

    public UnitValue(@NonNull final String unit, final int decimals) {
        this.type = Unit.getProgressType(unit);
        this.unit = unit;
        this.decimals = decimals;
    }

    public UnitValue(final double value, @NonNull final String unit) {
        this(unit, 2);
        this.value = value;

        this.inferDecimals();
    }

    public UnitValue(final int value, @NonNull final String unit) {
        this(unit, 0);
        this.value = value;

        this.inferDecimals();
    }

    public UnitValue(final double value, @NonNull final String unit, final int decimals) {
        this(unit, decimals);
        this.value = value;
    }

    public UnitValue(final int value, @NonNull final String unit, final int decimals) {
        this(unit, decimals);
        this.value = value;
    }

    public void set(final double value) {
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

        return Convert.from(this).best().getUnit();
    }

    public UnitValue getBest() {
        if (this.type == ProgressType.CUSTOM) {
            return this;
        }

        return Convert.from(this).best();
    }

    private void inferDecimals() {
        switch (this.type) {
            case COUNT:
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

    @SuppressLint("DefaultLocale")
    public String getFormattedValue() {
        final UnitValue value = this.getBest();

        switch (value.getUnit()) {
            case Unit.SECONDS:
                return String.format("0:%02d", (long)value.get());
            case Unit.MINUTES:
                return String.format("%d:%02d", (long)value.get(), (long)(value.get() * SECS_PER_MINUTE % SECS_PER_MINUTE));
            case Unit.HOURS:
                return String.format("%d:%02d:%02d", (long)value.get(), (long)(value.get() * SECS_PER_MINUTE % SECS_PER_MINUTE), (long)(value.get() * SECS_PER_MINUTE * SECS_PER_MINUTE % SECS_PER_MINUTE));
        }

        if (value.decimals == 0 || (long)value.get() == value.get()) {
            return String.valueOf((long)value.get());
        }

        return String.format("%." + this.decimals + "f", value.get());
    }

    public String getFormattedValue(@NonNull final String unit) {
        final UnitValue value = Convert.from(this).to(unit);

        if (this.decimals == 0) {
            return String.valueOf((long)value.get());
        }

        return String.format("%." + this.decimals + "f", value.get());
    }

    @Override
    public String toString() {
        final UnitValue best = this.getBest();

        switch (best.getUnit()) {
            case Unit.SECONDS:
            case Unit.MINUTES:
            case Unit.HOURS:
                return best.getFormattedValue();
        }

        return best.getFormattedValue() + best.getUnit();
    }

    public String toString(@NonNull final String unit) {
        switch (unit) {
            case Unit.SECONDS:
            case Unit.MINUTES:
            case Unit.HOURS:
                return this.getFormattedValue(unit);
        }

        return this.getFormattedValue(unit) + unit;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }

        final UnitValue unitValue = (UnitValue) o;
        return Double.compare(unitValue.value, this.value) == 0 &&
                this.decimals == unitValue.decimals &&
                this.type == unitValue.type &&
                this.unit.equals(unitValue.unit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.value, this.type, this.decimals, this.unit);
    }
}
