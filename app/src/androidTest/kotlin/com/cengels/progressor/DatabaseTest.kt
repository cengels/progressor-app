package com.cengels.progressor

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.cengels.progressor.db.AppDatabase
import com.cengels.progressor.db.daos.ProgressDao
import com.cengels.progressor.db.daos.ProgressItemDao
import com.cengels.progressor.db.entities.ProgressItem
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.io.IOException

/** Tests the application database. */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DatabaseTest {
    private lateinit var progressItemDao: ProgressItemDao
    private lateinit var progressDao: ProgressDao
    private lateinit var db: AppDatabase

    @BeforeAll
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        progressItemDao = db.progressItemDao()
        progressDao = db.progressDao()
    }

    @AfterAll
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun testWritingAndReadingProgressItems() {
        val progressItem = ProgressItem(0, "words", "written", 120_000.0, 100.0)
        val progressItem1 = ProgressItem(1, "days", "run", step = 100.0)
        val progressItem2 = ProgressItem(2, "strangers", "murdered", 100.0)
        this.progressItemDao.insert(progressItem, progressItem1, progressItem2)

        val progressItems: List<ProgressItem> = this.progressItemDao.getAll()
        assertThat(progressItems).containsExactlyInAnyOrder(progressItem, progressItem1, progressItem2)
    }

}