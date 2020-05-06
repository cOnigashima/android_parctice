package com.example.bindingmvvmpractice

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface MoodDao {
    // LiveData is a data holder class that can be observed within a given lifecycle.
    // Always holds/caches latest version of data. Notifies its active observers when the
    // data has changed. Since we are getting all the contents of the database,
    // we are notified whenever any of the database contents have changed.
    @Query("SELECT * from mood_table ORDER BY moodId ASC")
    fun getMoodsList(): LiveData<List<Mood>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(mood: Mood)

    @Query("DELETE FROM mood_table")
    suspend fun deleteAll()
}