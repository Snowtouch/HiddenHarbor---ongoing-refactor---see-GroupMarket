package com.snowtouch.hiddenharbor.ui

import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.snowtouch.hiddenharbor.ui.components.ApplicationBottomBar
import com.snowtouch.hiddenharbor.ui.components.TopBar
import com.snowtouch.hiddenharbor.ui.components.UserNotLoggedScreenContent
import com.snowtouch.hiddenharbor.viewmodel.GroupScreenViewModel
import com.snowtouch.hiddenharbor.viewmodel.UserState

@Composable
fun GroupScreen(
    navController: NavHostController,
    viewModel: GroupScreenViewModel
) {
    val user by viewModel.user.collectAsState()
    val userLoggedIn by viewModel.userLoggedIn.collectAsState()
    Scaffold(
        modifier = Modifier,
        bottomBar = { ApplicationBottomBar(navController) },
        topBar = { TopBar( caNavigateBack = true, navController = navController, searchFieldVisible = false)
        }
    ) { innerPadding ->
        if (userLoggedIn) {
            Text(text = "User logged in")
        } else {
            UserNotLoggedScreenContent(paddingValues = innerPadding, navController = navController)
        }
    }
}
@Composable
fun GroupListCard(modifier: Modifier = Modifier){
    Card(modifier){

    }
}
@Preview
@Composable
fun UserGroupScreenPreview(){
    val context = LocalContext.current
    GroupScreen(viewModel = GroupScreenViewModel(UserState), navController = NavHostController(context))
}