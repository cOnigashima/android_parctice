package com.example.myrxjavapractice


import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface JournalingContentDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(JournalingContent: JournalingContent) : Completable

    @Query("SELECT * FROM JournalingContent")
    // fun getAll(): Observable<List<JournalingContent>> これ、observableじゃなくて良くない？
    fun getAll(): Single<List<JournalingContent>>

    @Query("SELECT * FROM JournalingContent WHERE journalingContentId = :JournalingContentId")
    fun findById(JournalingContentId : Int): JournalingContent

    @Update
    fun update(JournalingContent: JournalingContent)

    @Delete
    fun delete(JournalingContent: JournalingContent)
}