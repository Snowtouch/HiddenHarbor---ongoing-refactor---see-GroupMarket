package com.snowtouch.hiddenharbor.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.snowtouch.hiddenharbor.model.Category
import com.snowtouch.hiddenharbor.model.Option
import com.snowtouch.hiddenharbor.model.categories

@Composable
fun AccountScreen(categories: List<Category>, navController: NavHostController){
    Scaffold(
        bottomBar = { ApplicationBottomBar(navController) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background)
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            categories.forEach { category ->
                CategoryItem(category = category)
                category.options.forEach { option ->
                    OptionItem(option = option)
                }
            }
        }
    }
}
@Composable
fun CategoryItem(category: Category) {
    Row(modifier = Modifier.padding(top = 12.dp)) {
        Text(text = category.name, style = MaterialTheme.typography.headlineLarge)
    }
}
@Composable
fun OptionItem(option: Option) {
    Row(
        modifier = Modifier
            .padding(start = 12.dp)
            .fillMaxWidth()
            .size(height = 48.dp, width = 300.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = option.name, style = MaterialTheme.typography.titleMedium)
    }
    Divider()
}
@Preview
@Composable
fun AccountScreenPreview(){
    AccountScreen(categories, navController = NavHostController(LocalContext.current))
}