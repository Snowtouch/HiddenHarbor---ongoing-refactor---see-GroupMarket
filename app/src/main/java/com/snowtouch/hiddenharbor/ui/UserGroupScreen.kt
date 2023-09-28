package com.snowtouch.hiddenharbor.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController

@Composable
fun UserGroupScreen(navController: NavHostController) {
    Scaffold(
        modifier = Modifier,
        bottomBar = { ApplicationBottomBar(navController) }
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
    UserGroupScreen(navController = NavHostController(context))
}