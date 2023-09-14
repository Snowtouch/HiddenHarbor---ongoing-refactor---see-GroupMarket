package com.snowtouch.hiddenharbor.ui

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.snowtouch.hiddenharbor.R

enum class AppRoute(@StringRes val title: Int){
    StartScreen(title = R.string.start_screen_nav_title),
    AccountScreen(title = R.string.your_account_screen_nav_title)
}
@Composable
fun NavigationComponent(navController: NavHostController){
    NavHost(navController = navController, startDestination = AppRoute.StartScreen.name){
        composable(route = AppRoute.StartScreen.name) {
            StartScreen(navController)
        }
        composable(route = AppRoute.AccountScreen.name){

        }
    }
}