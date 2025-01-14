package com.example.test_project_alarm

import android.app.Application
import com.example.test_project_alarm.database.DataBaseProvider

class AppClass : Application() {

    override fun onCreate() {
        super.onCreate()
        INSTANCE=this
        initRoomDB()
    }

    fun initRoomDB(){
        DataBaseProvider.applicationContext = applicationContext
    }

    companion object{

        internal lateinit var INSTANCE: AppClass
            private set
    }
}