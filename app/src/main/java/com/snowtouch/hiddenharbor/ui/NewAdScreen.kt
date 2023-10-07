package com.snowtouch.hiddenharbor.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import com.snowtouch.hiddenharbor.ui.components.TopBar
import com.snowtouch.hiddenharbor.ui.components.UserNotLoggedScreenContent
import com.snowtouch.hiddenharbor.viewmodel.NewAdScreenViewModel
import com.snowtouch.hiddenharbor.viewmodel.UserState

@Composable
fun NewAdScreen(
    navController: NavHostController,
    viewModel: NewAdScreenViewModel
) {
    val user by viewModel.user.collectAsState()
    val userLoggedIn by viewModel.userLoggedIn.collectAsState()

    Scaffold(
        modifier = Modifier,
        topBar = {
            TopBar(caNavigateBack = true, navController = navController, searchFieldVisible = false) },

    ) { innerPadding ->
        if (!userLoggedIn) { //TEST USER VARIABLE - TO REMOVE
            val scrollState = rememberScrollState()
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(12.dp)
                    .verticalScroll(scrollState)
                    .sizeIn(maxWidth = 370.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
                ) {
                adTextField(
                    modifier = Modifier,
                    text = "Title",
                    value = "",
                    onValueChange = {})
                adTextField(
                    modifier = Modifier
                        .sizeIn(minHeight = 200.dp),
                    text = "Description",
                    value = "",
                    onValueChange = {})
                }
        } else {
            UserNotLoggedScreenContent(
                paddingValues = innerPadding,
                navController = navController
            )
        }
    }
}
@Composable
fun adTextField(
    modifier: Modifier,
    text: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    Text(
        text = text,
        modifier = Modifier. padding(bottom = 4.dp),
        style = MaterialTheme.typography.titleMedium)
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp)
    )
}
@Preview
@Composable
fun newAdScreenPreview(){
    val navController = NavHostController(LocalContext.current)
    val viewModel =  NewAdScreenViewModel(UserState)
    NewAdScreen(navController = navController, viewModel = viewModel)
}