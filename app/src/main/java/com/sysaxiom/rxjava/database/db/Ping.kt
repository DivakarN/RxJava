package com.sysaxiom.rxjava.database.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ping")
data class Ping(
    @PrimaryKey
    val status: Int,
    val success: Boolean,
    val message: String
)