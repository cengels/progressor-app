package com.cengels.progressor.units;

import androidx.annotation.NonNull;

public interface Arithmetic<T extends Arithmetic<T>> {
    T multiply(@NonNull final T value);

    T divideBy(@NonNull final T value);

    T add(@NonNull final T value);

    T subtract(@NonNull final T value);
}
