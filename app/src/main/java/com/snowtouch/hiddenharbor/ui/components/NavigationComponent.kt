package com.snowtouch.hiddenharbor.ui.components

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.snowtouch.hiddenharbor.data.model.accountScreenCategories
import com.snowtouch.hiddenharbor.ui.AccountScreen
import com.snowtouch.hiddenharbor.ui.GroupScreen
import com.snowtouch.hiddenharbor.ui.StartScreen
import com.snowtouch.hiddenharbor.viewmodel.AccountScreenViewModel
import com.snowtouch.hiddenharbor.viewmodel.GroupScreenViewModel

enum class AppRoute() {
    StartScreen,
    AccountScreen,
    GroupScreen
}
@Composable
fun NavigationComponent(
    navController: NavHostController,
    accountScreenViewModel: AccountScreenViewModel
) {
    NavHost(navController = navController, startDestination = AppRoute.StartScreen.name) {
        composable(route = AppRoute.StartScreen.name) {
            CurrentScreen.name = AppRoute.StartScreen.name
            StartScreen(navController)
        }
        composable(route = AppRoute.AccountScreen.name) {
            CurrentScreen.name = AppRoute.AccountScreen.name
            AccountScreen(accountScreenCategories, navController, accountScreenViewModel)
        }
        composable(route = AppRoute.GroupScreen.name) {
            CurrentScreen.name = AppRoute.GroupScreen.name
            GroupScreen(GroupScreenViewModel(), navController)
        }
    }
}
object CurrentScreen {
    var name: String? = null
}
