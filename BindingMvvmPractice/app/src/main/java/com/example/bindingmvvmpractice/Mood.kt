package com.example.bindingmvvmpractice

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "mood_table")
data class Mood(@PrimaryKey (autoGenerate = true) val moodId: Int,
                @ColumnInfo(name = "mood") val mood: String,
                val value : Int)