package com.cengels.progressor.db.converters

import androidx.room.TypeConverter
import com.cengels.progressor.enums.MaximumType

class EnumConverter {
    @TypeConverter
    fun fromMaximumType(type: MaximumType): String = type.name

    @TypeConverter
    fun toMaximumType(type: String): MaximumType = MaximumType.valueOf(type)
}