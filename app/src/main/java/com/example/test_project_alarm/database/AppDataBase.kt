package com.example.test_project_alarm.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.test_project_alarm.database.dao.PictureDao
import com.example.test_project_alarm.database.entities.PictureEntity

@Database(
    version = 1,
    entities = [
        PictureEntity::class
    ],
)
abstract class AppDataBase : RoomDatabase() {
    abstract fun getPictureDao(): PictureDao
}