package com.example.test_project_alarm.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.test_project_alarm.home_screen.HomeScreen
import com.example.test_project_alarm.map_screen.MapScreen
import com.example.test_project_alarm.photo_list.PhotoListScreen
import com.example.test_project_alarm.setting_screen.SettingScreen

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Settings : Screen("setting")
    data object AllPhoto : Screen("all_photo")
    data object Map : Screen("map")
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavigationStack(navHostController: NavHostController, modifier: Modifier) {
    NavHost(navController = navHostController, startDestination = Screen.Home.route, modifier = modifier) {
        composable(route = Screen.Home.route) {
            HomeScreen()
        }
        composable(route = Screen.AllPhoto.route) {
            PhotoListScreen()
        }
        composable(route = Screen.Settings.route) {
            SettingScreen()
        }
        composable(route = Screen.Map.route) {
            MapScreen()
        }
    }
}