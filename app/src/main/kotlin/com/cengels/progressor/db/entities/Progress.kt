package com.cengels.progressor.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import java.sql.Date

@Entity(foreignKeys = [ForeignKey(entity = ProgressItem::class, parentColumns = ["id"], childColumns = ["user_id"], onDelete = CASCADE)])
data class Progress (
    @PrimaryKey(autoGenerate = true) val id: Long,
    val date: Date,
    val value: Double,
    @ColumnInfo(name = "progress_item_id") val progressItemId: Long
)
