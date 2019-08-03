package com.cengels.progressor.models;

import androidx.annotation.NonNull;
import com.cengels.progressor.enums.ProgressType;
import com.cengels.progressor.units.UnitValue;

public class ProgressItem {
    private int id;
    @NonNull
    private String label = "";
    @NonNull
    private UnitValue value;
    @NonNull
    private UnitValue goal;
    @NonNull
    private UnitValue step;

    public ProgressType getType() {
        return this.value.getType();
    }

    public int getId() {
        return this.id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    @NonNull
    public String getLabel() {
        return this.label;
    }

    public void setLabel(@NonNull final String label) {
        this.label = label;
    }

    @NonNull
    public String getUnitLabel() {
        return this.getValue().getBestUnit() + " " + this.label;
    }

    @NonNull
    public UnitValue getValue() {
        return this.value;
    }

    public void setValue(@NonNull final UnitValue value) {
        this.value = value;
    }

    @NonNull
    public UnitValue getGoal() {
        return this.goal;
    }

    public void setGoal(@NonNull final UnitValue goal) {
        this.goal = goal;
    }

    public UnitValue getProgress() {
        return this.value.divideBy(this.goal).multiply(100);
    }

    @NonNull
    public UnitValue getStep() {
        return this.step;
    }

    public void setStep(@NonNull final UnitValue step) {
        this.step = step;
    }
}
