package com.example.test_project_alarm.database

import android.content.Context
import androidx.room.Room
import com.example.test_project_alarm.database.dao.PictureDao

object DataBaseProvider {
    lateinit var applicationContext: Context

    val appDataBase by lazy {
        Room.databaseBuilder(
            context = applicationContext,
            klass = AppDataBase::class.java,
            name = "gallery_db"
        )
            .addMigrations()
            .build()
    }

    fun getPictureDao(): PictureDao{
        return appDataBase.getPictureDao()
    }
}