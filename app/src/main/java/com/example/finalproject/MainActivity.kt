package com.example.finalproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.finalproject.ui.theme.FinalProjectTheme
import com.example.finalproject.ui_components.InfoScreen
import com.example.finalproject.ui_components.MainScreen
import com.example.finalproject.utils.ListItem
import com.example.finalproject.utils.Routes
import dagger.hilt.android.AndroidEntryPoint



@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            setContent {
                val navController = rememberNavController()
                var item: ListItem? = null
                FinalProjectTheme {
                    NavHost(
                        navController = navController,
                        startDestination = Routes.MAIN_SCREEN
                    ) {
                        composable(Routes.MAIN_SCREEN) {
                            MainScreen { listItem ->
                                item = listItem
                                navController.navigate(Routes.INFO_SCREEN)
                            }
                        }

                        composable(Routes.INFO_SCREEN) {
                            InfoScreen(item = item!!)
                        }
                    }
                }
            }
        }
    }
}