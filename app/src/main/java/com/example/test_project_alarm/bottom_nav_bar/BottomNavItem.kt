package com.example.test_project_alarm.bottom_nav_bar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Place
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.test_project_alarm.ui.theme.Test_Project_AlarmTheme

data class BottomNavItem(
    val name: String,
    val selectedItem: ImageVector,
    val unselectedItem: ImageVector,
    val screenName: String,
)

val navItemList = mutableListOf(
    BottomNavItem(
        name = "Начало",
        selectedItem = Icons.Rounded.Home,
        unselectedItem = Icons.Outlined.Home,
        screenName ="home"
    ),
    BottomNavItem(
        name = "Все фото",
        selectedItem = Icons.Rounded.PlayArrow,
        unselectedItem = Icons.Outlined.PlayArrow,
        screenName = "all_photo"
    ),
    BottomNavItem(
        name = "Карта",
        selectedItem = Icons.Rounded.Place,
        unselectedItem = Icons.Outlined.Place,
        screenName = "map"
    ),
    BottomNavItem(
        name = "Настройки",
        selectedItem = Icons.Rounded.Settings,
        unselectedItem = Icons.Outlined.Settings,
        screenName = "setting"
    ),
)

@Composable
fun AppNavigationBar(navHostController: NavHostController) {

    val selectedItem = remember {
        mutableIntStateOf(0)
    }
    NavigationBar {
        navItemList.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItem.intValue == index,
                onClick = {
                    selectedItem.intValue = index
                    navHostController.navigate(item.screenName)
                },
                label = { Text(text = item.name) },
                icon = {
                    Icon(
                        imageVector = if (selectedItem.intValue == index)
                            navItemList[index].selectedItem
                        else navItemList[index].unselectedItem,
                        contentDescription = item.name
                    )
                })
        }
    }
}

@Preview
@Composable
fun PreviewNavBar() {
    Test_Project_AlarmTheme {
//        AppNavigationBar()
    }
}