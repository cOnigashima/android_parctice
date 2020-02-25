package com.example.myrxjavapractice


import androidx.room.*
import io.reactivex.Completable

@Dao
interface JournalingContentDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(JournalingContent: JournalingContent) : Completable

    @Query("SELECT * FROM JournalingContent")
    fun getAll(): List<JournalingContent>

    @Query("SELECT * FROM JournalingContent WHERE journalingContentId = :JournalingContentId")
    fun getOneById(JournalingContentId : Int): JournalingContent

    @Update
    fun update(JournalingContent: JournalingContent)

    @Delete
    fun delete(JournalingContent: JournalingContent)
}