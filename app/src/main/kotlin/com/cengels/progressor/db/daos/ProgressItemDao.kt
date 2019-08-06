package com.cengels.progressor.db.daos

import androidx.room.*
import com.cengels.progressor.db.entities.ProgressItem

@Dao
interface ProgressItemDao {
    @Query("SELECT * FROM progress_items")
    fun getAll(): List<ProgressItem>

    @Insert
    fun insert(vararg progressItems: ProgressItem)

    @Update
    fun update(vararg progressItems: ProgressItem)

    @Delete
    fun delete(progressItem: ProgressItem)

}