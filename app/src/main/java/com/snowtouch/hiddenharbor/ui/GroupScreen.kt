package com.snowtouch.hiddenharbor.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.snowtouch.hiddenharbor.viewmodel.GroupScreenViewModel

@Composable
fun GroupScreen(
    viewModel: GroupScreenViewModel,
    navController: NavHostController
) {
    val showGroupMenu = remember { mutableStateOf(viewModel.appUiState.value.groupsButtonEnabled) }
    Scaffold(
        modifier = Modifier,
        bottomBar = { ApplicationBottomBar(navController) },
        topBar = {
            TopAppBar(
                title = {},
                actions = {
                    GroupsButton(
                        onClick = { showGroupMenu.value = !showGroupMenu.value })}
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)
        ) {

        }
    }
}
@Preview
@Composable
fun UserGroupScreenPreview(){
    val context = LocalContext.current
    GroupScreen(viewModel = GroupScreenViewModel(), navController = NavHostController(context))
}