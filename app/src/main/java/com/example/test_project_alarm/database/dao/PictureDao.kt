package com.example.test_project_alarm.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.test_project_alarm.database.entities.PictureEntity

@Dao
interface PictureDao {

    @Insert
    fun addPicture(pictureEntity: PictureEntity)

    @Query("SELECT * FROM PictureEntity")
    fun getAllPicture():List<PictureEntity>
}