package com.cengels.progressor.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cengels.progressor.db.daos.ProgressDao
import com.cengels.progressor.db.daos.ProgressItemDao
import com.cengels.progressor.db.entities.ProgressItem

@Database(entities = [ProgressItem::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun progressItemDao(): ProgressItemDao
    abstract fun progressDao(): ProgressDao
}
