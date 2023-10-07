package com.snowtouch.hiddenharbor.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.snowtouch.hiddenharbor.data.model.accountScreenCategories
import com.snowtouch.hiddenharbor.ui.AccountScreen
import com.snowtouch.hiddenharbor.ui.FavoritesScreen
import com.snowtouch.hiddenharbor.ui.GroupScreen
import com.snowtouch.hiddenharbor.ui.NewAdScreen
import com.snowtouch.hiddenharbor.ui.StartScreen
import com.snowtouch.hiddenharbor.viewmodel.AccountScreenViewModel
import com.snowtouch.hiddenharbor.viewmodel.FavoritesScreenViewModel
import com.snowtouch.hiddenharbor.viewmodel.GroupScreenViewModel
import com.snowtouch.hiddenharbor.viewmodel.NewAdScreenViewModel
import com.snowtouch.hiddenharbor.viewmodel.UserState

enum class AppRoute {
    StartScreen,
    FavoritesScreen,
    NewAdScreen,
    GroupScreen,
    AccountScreen

}
@Composable
fun NavigationComponent(
    navController: NavHostController,
    accountScreenViewModel: AccountScreenViewModel
) {
    val user by accountScreenViewModel.user.collectAsState()

    NavHost(navController = navController, startDestination = AppRoute.StartScreen.name) {
        composable(route = AppRoute.StartScreen.name) {
            CurrentScreen.name = AppRoute.StartScreen.name
            StartScreen(navController)
        }
        composable(route = AppRoute.FavoritesScreen.name) {
            CurrentScreen.name = AppRoute.FavoritesScreen.name
            FavoritesScreen(navController, FavoritesScreenViewModel(UserState))
        }
        composable(route = AppRoute.NewAdScreen.name) {
            CurrentScreen.name = AppRoute.NewAdScreen.name
            NewAdScreen(navController, NewAdScreenViewModel(UserState))
        }
        composable(route = AppRoute.GroupScreen.name) {
            CurrentScreen.name = AppRoute.GroupScreen.name
            GroupScreen(navController, GroupScreenViewModel(UserState))
        }
        composable(route = AppRoute.AccountScreen.name) {
            CurrentScreen.name = AppRoute.AccountScreen.name
            AccountScreen(accountScreenCategories, navController, accountScreenViewModel)
        }

    }
}
object CurrentScreen {
    var name: String? = null
}
