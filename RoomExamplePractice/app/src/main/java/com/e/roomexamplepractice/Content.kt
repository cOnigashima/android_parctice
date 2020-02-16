package com.e.roomexamplepractice

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Content(
    @PrimaryKey var id: Int,
    var contentText: String
)