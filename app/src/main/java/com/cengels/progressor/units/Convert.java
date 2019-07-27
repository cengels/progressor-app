package com.cengels.progressor.units;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.cengels.progressor.ArrayExtensions;
import com.cengels.progressor.enums.ProgressType;
import com.cengels.progressor.exceptions.IllegalConversionException;

import java.util.IllegalFormatConversionException;

public class Convert {
    @NonNull
    private final UnitValue value;
    @Nullable
    private String[] units;
    private boolean checked = false;

    private Convert(@NonNull UnitValue unitValue) {
        this.value = unitValue;
    }

    @NonNull
    public static Convert from(double value, @NonNull String fromUnit) {
        return new Convert(new UnitValue(value, fromUnit));
    }

    @NonNull
    public static Convert from(@NonNull UnitValue unitValue) {
        return new Convert(unitValue);
    }

    private ProgressType getProgressType() {
        return Unit.getProgressType(this.value.getUnit());
    }

    @Nullable
    private String[] getUnits() {
        if (this.units == null && !this.checked) {
            this.units = Unit.getUnits(this.getProgressType());
            this.checked = true;
        }

        return this.units;
    }

    @NonNull
    public UnitValue getValue() {
        return this.value;
    }

    public UnitValue to(@NonNull String toUnit) {
        if (this.value.getUnit().equals(toUnit)) {
            return this.value;
        }

        if (this.getUnits() == null) {
            throw new IllegalConversionException(this.value.getUnit(), toUnit);
        }

        final String[] units = this.getUnits();

        int index = ArrayExtensions.indexOf(units, this.value.getUnit());
        final int indexTarget = ArrayExtensions.indexOf(units, toUnit);

        if (index == -1 || indexTarget == -1) {
            throw new IllegalConversionException(this.value.getUnit(), toUnit);
        }

        double value = this.value.get();

        if (index < indexTarget) {
            for (int i = index; i < indexTarget; i++) {
                value /= Unit.conversionTable.get(units[i]);
            }
        } else {
            for (int i = index; i > indexTarget; i--) {
                value *= Unit.conversionTable.get(units[i - 1]);
            }
        }

        return new UnitValue(value, toUnit);
    }

    public UnitValue best() {
        if (this.getUnits() == null) {
            return this.value;
        }

        final String[] units = this.getUnits();

        int index = ArrayExtensions.indexOf(units, this.value.getUnit());

        double value = this.value.get();
        String unit = this.value.getUnit();

        while ((index <= units.length - 1 && value < 1 && Unit.conversionTable.containsKey(units[index - 1]))
                || (index >= 0 && Unit.conversionTable.containsKey(unit) && value >= Unit.conversionTable.get(unit))) {
            if (value < 1) {
                value *= Unit.conversionTable.get(units[index - 1]);
                index--;
                unit = units[index];
            } else {
                value /= Unit.conversionTable.get(unit);
                index++;
                unit = units[index];
            }
        }

        return new UnitValue(value, unit);
    }
}
