package com.cengels.progressor.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import com.cengels.progressor.enums.MaximumType
import java.util.*

@Entity(foreignKeys = [ForeignKey(entity = ProgressItem::class, parentColumns = ["id"], childColumns = ["id"], onDelete = CASCADE)])
data class Progress (
        @PrimaryKey(autoGenerate = true) val id: Long,
        @ColumnInfo(name = "progress_item_id") val progressItemId: Long,
        val date: Date,
        val value: Double,
        val maximumType: MaximumType,
        val maximumPerPeriod: Int
)
