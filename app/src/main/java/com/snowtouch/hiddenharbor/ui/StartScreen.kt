package com.snowtouch.hiddenharbor.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.snowtouch.hiddenharbor.ui.components.ApplicationBottomBar
import com.snowtouch.hiddenharbor.ui.components.MainTopBar
import com.snowtouch.hiddenharbor.ui.theme.HiddenHarborTheme


@Composable
fun StartScreen(navController: NavHostController){
    Scaffold(
        topBar = { MainTopBar(navController = navController, searchFieldVisible = true) },
        bottomBar = { ApplicationBottomBar(navController) }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StartScreenPreview() {
    HiddenHarborTheme {
        val context = LocalContext.current
        StartScreen(navController = NavHostController(context))
    }
}