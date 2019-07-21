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
}
