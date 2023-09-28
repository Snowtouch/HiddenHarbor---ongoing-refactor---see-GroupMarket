package com.snowtouch.hiddenharbor.ui.components

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.snowtouch.hiddenharbor.data.model.accountScreenCategories
import com.snowtouch.hiddenharbor.ui.AccountScreen
import com.snowtouch.hiddenharbor.ui.StartScreen
import com.snowtouch.hiddenharbor.viewmodel.AccountScreenViewModel

enum class AppRoute(val title: String){
    StartScreen(title = "Home"),
    AccountScreen(title = "Account")
}
@Composable
fun NavigationComponent(
    navController: NavHostController,
    accountScreenViewModel: AccountScreenViewModel
) {
    NavHost(navController = navController, startDestination = AppRoute.StartScreen.name){
        composable(route = AppRoute.StartScreen.name) {
            StartScreen(navController)
        }
        composable(route = AppRoute.AccountScreen.name){
            AccountScreen(categories = accountScreenCategories, navController, accountScreenViewModel)
        }
    }
}