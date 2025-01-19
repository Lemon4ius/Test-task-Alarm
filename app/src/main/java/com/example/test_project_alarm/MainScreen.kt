package com.example.test_project_alarm

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.test_project_alarm.bottom_nav_bar.AppNavigationBar
import com.example.test_project_alarm.navigation.NavigationStack

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen(){
    val navController = rememberNavController()
    Scaffold(bottomBar = { AppNavigationBar(navController)}) {
        NavigationStack(navHostController =  navController, modifier = Modifier.padding(it))
    }
}