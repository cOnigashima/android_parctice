package com.example.myrxjavapractice

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(JournalingContent::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun contentDao(): JournalingContentDao
}