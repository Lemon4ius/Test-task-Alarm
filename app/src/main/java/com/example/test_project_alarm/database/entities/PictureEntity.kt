package com.example.test_project_alarm.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PictureEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val name: String,
    val date: Long,
    val uri: String,
    val latitude: Double,
    val longitude: Double
)