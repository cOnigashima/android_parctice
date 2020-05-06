package com.example.bindingmvvmpractice


import androidx.lifecycle.LiveData

/**
 * Abstracted Repository as promoted by the Architecture Guide.
 * https://developer.android.com/topic/libraries/architecture/guide.html
 */
class MoodRepository(private val moodDao: MoodDao) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val allMoods: LiveData<List<Mood>> = moodDao.getMoodsList()

    suspend fun insert(mood: Mood) {
        moodDao.insert(mood)
    }
}
