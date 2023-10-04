package com.snowtouch.hiddenharbor.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.snowtouch.hiddenharbor.data.model.Ad
import com.snowtouch.hiddenharbor.sampledata.sampleCardList
import com.snowtouch.hiddenharbor.ui.components.AdListComponent
import com.snowtouch.hiddenharbor.ui.components.ApplicationBottomBar
import com.snowtouch.hiddenharbor.ui.components.TopBar

@Composable
fun FavoritesScreen(
    adList: List<Ad>?,
    navController: NavHostController
){
    Scaffold(
        modifier = Modifier,
        topBar = { 
            TopBar(caNavigateBack = true, navController = navController, searchFieldVisible = false) },
        bottomBar = { ApplicationBottomBar(navController = navController)},
    ) { paddingValues ->  
        AdListComponent(adList = adList, modifier = Modifier.padding(paddingValues))
    }
}
@Preview
@Composable
fun FavoritesScreenPreview(){
    val navController = NavHostController(LocalContext.current)
    FavoritesScreen(sampleCardList, navController)
}