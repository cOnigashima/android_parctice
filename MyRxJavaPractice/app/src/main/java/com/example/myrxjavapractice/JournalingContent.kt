package com.example.myrxjavapractice

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class JournalingContent(
    @PrimaryKey var journalingContentId: Int,
    var jornalingContentTitle: String,
    var journalingContentText: String,
    var createDate: String
)