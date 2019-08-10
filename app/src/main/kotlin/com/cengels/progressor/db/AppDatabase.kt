package com.cengels.progressor.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.cengels.progressor.db.converters.DateConverter
import com.cengels.progressor.db.daos.ProgressDao
import com.cengels.progressor.db.daos.ProgressItemDao
import com.cengels.progressor.db.entities.Progress
import com.cengels.progressor.db.entities.ProgressItem

@TypeConverters(DateConverter::class)
@Database(entities = [ProgressItem::class, Progress::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun progressItemDao(): ProgressItemDao
    abstract fun progressDao(): ProgressDao
}
