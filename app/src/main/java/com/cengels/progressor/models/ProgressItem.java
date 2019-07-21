package com.cengels.progressor.models;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.cengels.progressor.enums.ProgressType;

public class ProgressItem {
    private int id;
    @NonNull
    private String label = "";
    @NonNull
    private UnitValue value;
    @Nullable
    private UnitValue maxValue;

    public ProgressType getType() {
        return this.value.getType();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getLabel() {
        return label;
    }

    public void setLabel(@NonNull String label) {
        this.label = label;
    }

    @NonNull
    public UnitValue getValue() {
        return this.value;
    }

    public void setValue(@NonNull UnitValue value) {
        this.value = value;
    }

    @Nullable
    public UnitValue getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(@Nullable UnitValue maxValue) {
        this.maxValue = maxValue;
    }
}
