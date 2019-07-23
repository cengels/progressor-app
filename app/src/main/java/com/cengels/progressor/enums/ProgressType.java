package com.cengels.progressor.enums;

import com.cengels.progressor.units.Unit;
import com.cengels.progressor.units.UnitDescriptor;

public enum ProgressType {
    @UnitDescriptor(Unit.NONE)
    COUNT,
    @UnitDescriptor(Unit.PERCENT)
    PERCENTAGE,
    @UnitDescriptor({Unit.GRAMS, Unit.KILOGRAMS, Unit.TONS})
    WEIGHT,
    @UnitDescriptor({Unit.MILLIMETERS, Unit.CENTIMETERS, Unit.METERS, Unit.KILOMETERS})
    HEIGHT,
    @UnitDescriptor({Unit.MILLIMETERS, Unit.CENTIMETERS, Unit.METERS, Unit.KILOMETERS})
    DISTANCE,
    @UnitDescriptor({Unit.MILLILITERS, Unit.LITERS})
    LIQUID,
    @UnitDescriptor({Unit.CALORIES, Unit.KILOCALORIES})
    CALORIES,
    @UnitDescriptor({Unit.SECONDS, Unit.MINUTES, Unit.HOURS})
    TIME,
    @UnitDescriptor({Unit.DAYS, Unit.MONTHS, Unit.YEARS})
    DAYS,
    CUSTOM
}
