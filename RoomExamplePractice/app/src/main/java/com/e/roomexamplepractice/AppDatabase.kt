package com.e.roomexamplepractice

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Content::class), version = 3)
abstract class AppDatabase : RoomDatabase() {
    abstract fun contentDao(): ContentDao
}
