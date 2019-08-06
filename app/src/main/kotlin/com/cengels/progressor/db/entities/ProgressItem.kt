package com.cengels.progressor.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "progress_items")
data class ProgressItem (
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(defaultValue = "") val label: String,
    val goal: Double?,
    @ColumnInfo(defaultValue = "1.0") val step: Double
)