package com.cengels.progressor.db.daos

import androidx.room.*
import com.cengels.progressor.db.entities.Progress
import com.cengels.progressor.db.entities.ProgressItem

@Dao
interface ProgressDao {
    @Query("SELECT * FROM progress")
    fun getAll(): List<Progress>

    @Insert
    fun insert(vararg progress: Progress)

    @Update
    fun update(vararg progress: Progress)

    @Delete
    fun delete(progress: Progress)
}
