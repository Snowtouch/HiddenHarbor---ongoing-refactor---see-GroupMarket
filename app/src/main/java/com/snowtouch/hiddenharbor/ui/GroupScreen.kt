package com.snowtouch.hiddenharbor.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.snowtouch.hiddenharbor.ui.components.AppRoute
import com.snowtouch.hiddenharbor.ui.components.ApplicationBottomBar
import com.snowtouch.hiddenharbor.ui.components.TopBar
import com.snowtouch.hiddenharbor.viewmodel.GroupScreenViewModel

@Composable
fun GroupScreen(
    viewModel: GroupScreenViewModel,
    navController: NavHostController
) {
    val userLoggedIn by viewModel.userLoggedInState.collectAsState()
    Scaffold(
        modifier = Modifier,
        bottomBar = { ApplicationBottomBar(navController) },
        topBar = { TopBar( caNavigateBack = true, navController = navController, searchFieldVisible = false)
        }
    ) { innerPadding ->
        if (!userLoggedIn) {
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(24.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Sign in to view available groups", modifier = Modifier.padding(bottom = 8.dp))
                ElevatedButton(
                    onClick = { navController.navigate(AppRoute.AccountScreen.name) },
                    shape = MaterialTheme.shapes.small,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Text(text = "Login")
                }
            }
        } else {
            Text(text = "User logged in")
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
    GroupScreen(viewModel = GroupScreenViewModel(), navController = NavHostController(context))
}