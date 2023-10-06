package com.snowtouch.hiddenharbor.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.snowtouch.hiddenharbor.ui.components.AdListComponent
import com.snowtouch.hiddenharbor.ui.components.ApplicationBottomBar
import com.snowtouch.hiddenharbor.ui.components.TopBar
import com.snowtouch.hiddenharbor.ui.components.UserNotLoggedScreenContent
import com.snowtouch.hiddenharbor.viewmodel.FavoritesScreenViewModel
import com.snowtouch.hiddenharbor.viewmodel.UserState

@Composable
fun FavoritesScreen(
    navController: NavHostController,
    viewModel: FavoritesScreenViewModel
) {
    val user by viewModel.user.collectAsState()
    val userLoggedIn by viewModel.userLoggedIn.collectAsState()

    Scaffold(
        modifier = Modifier,
        topBar = { 
            TopBar(caNavigateBack = true, navController = navController, searchFieldVisible = false) },
        bottomBar = { ApplicationBottomBar(navController = navController)},
    ) { paddingValues ->
        if (userLoggedIn) {
            AdListComponent(adList = user.ads, modifier = Modifier.padding(paddingValues))
        } else {
            UserNotLoggedScreenContent(paddingValues = paddingValues, navController = navController)
        }
    }
}
@Preview
@Composable
fun FavoritesScreenPreview(){
    val navController = NavHostController(LocalContext.current)
    FavoritesScreen(navController, FavoritesScreenViewModel(UserState))
}