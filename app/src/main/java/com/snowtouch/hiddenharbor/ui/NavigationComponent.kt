package com.snowtouch.hiddenharbor.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.snowtouch.hiddenharbor.model.categories
import com.snowtouch.hiddenharbor.viewmodel.LoginScreenViewModel

enum class AppRoute(val title: String){
    StartScreen(title = "Home"),
    AccountScreen(title = "Account")
}
@Composable
fun NavigationComponent(
    navController: NavHostController,
    loginScreenViewModel: LoginScreenViewModel
) {
    NavHost(navController = navController, startDestination = AppRoute.StartScreen.name){
        composable(route = AppRoute.StartScreen.name) {
            StartScreen(navController)
        }
        composable(route = AppRoute.AccountScreen.name){
            AccountScreen(categories = categories, navController, loginScreenViewModel)
        }
    }
}