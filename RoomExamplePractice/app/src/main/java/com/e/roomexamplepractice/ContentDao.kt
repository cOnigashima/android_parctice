package com.e.roomexamplepractice

import androidx.room.*

@Dao
interface ContentDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun create(content: Content)

    @Query("SELECT * FROM Content")
    fun getAll(): List<Content>

    @Query("SELECT * FROM Content WHERE id = :contentId")
    fun getOne(contentId : Int): Content

    @Update
    fun update(content: Content)

    @Delete
    fun delete(content: Content)
}