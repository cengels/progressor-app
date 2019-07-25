package com.cengels.progressor.units;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.cengels.progressor.ArrayExtensions;
import com.cengels.progressor.enums.ProgressType;

public class Convert {
    private final String fromUnit;
    private String[] units;
    private boolean checked = false;
    private double value = Double.MIN_VALUE;

    private Convert(@NonNull String fromUnit) {
        this.fromUnit = fromUnit;
    }

    public static Convert from(@NonNull String fromUnit) {
        return new Convert(fromUnit);
    }

    public Convert value(double value) {
        this.value = value;

        return this;
    }

    private ProgressType getProgressType() {
        return Unit.getProgressType(this.fromUnit);
    }

    @Nullable
    private String[] getUnits() {
        if (this.units == null && !this.checked) {
            this.units = Unit.getUnits(this.getProgressType());
            this.checked = true;
        }

        return this.units;
    }

    @Nullable
    public String bestUnit() {
        if (!Unit.conversionTable.containsKey(this.fromUnit) || this.getUnits() == null) {
            return null;
        }

        final String[] units = this.getUnits();

        int index = ArrayExtensions.indexOf(units, this.fromUnit);

        if (index == -1) {
            throw new IllegalArgumentException("The passed units array does not contain this object's unitSuffix.");
        }

        double value = this.value;
        String unit = this.fromUnit;

        // TODO: Double-check this. Or write tests. That's a nice thought. Tests!
        while ((index < units.length - 1 && value < 1 && Unit.conversionTable.containsKey(units[index + 1]))
                || (index >= 0 && value >= Unit.conversionTable.get(unit))) {
            if (value < 1) {
                // This is a remnant from early code. Since UnitValues always use the smallest value as their
                // internal value, this branch should never be entered.
                value *= Unit.conversionTable.get(units[index + 1]);
                index--;
                unit = units[index];
            } else {
                value /= Unit.conversionTable.get(unit);
                index++;
                unit = units[index];
            }
        }

        return unit;
    }

    public double to(@NonNull String toUnit) {
        if (this.value == Double.MIN_VALUE) {
            throw new RuntimeException("Value wasn't specified. Did you forget to call value()?");
        }

        if (this.fromUnit.equals(toUnit)) {
            return this.value;
        }

        if (!Unit.conversionTable.containsKey(this.fromUnit) || this.getUnits() == null) {
            return this.value;
        }

        final String[] units = this.getUnits();

        int index = ArrayExtensions.indexOf(units, this.fromUnit);
        final int indexTarget = ArrayExtensions.indexOf(units, toUnit);

        if (index == -1 || indexTarget == -1) {
            throw new IllegalArgumentException("Index not found.");
        }

        double value = this.value;

        if (index < indexTarget) {
            for (int i = index; i < indexTarget; i++) {
                value /= Unit.conversionTable.get(units[i]);
            }
        } else {
            // This is a remnant from early code. Since UnitValues always use the smallest value as their
            // internal value, this branch should never be entered.
            for (int i = index; i >= indexTarget; i--) {
                value *= Unit.conversionTable.get(units[i - 1]);
            }
        }

        return value;
    }

    public double best() {
        final String bestUnit = this.bestUnit();

        if (bestUnit == null) {
            return this.value;
        }

        return this.to(bestUnit);
    }
}
