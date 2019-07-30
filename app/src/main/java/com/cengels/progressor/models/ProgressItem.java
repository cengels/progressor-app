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

    public int getProgress() {
        return (int) Math.round(this.value.get() / this.goal.get() * 100);
    }
}
