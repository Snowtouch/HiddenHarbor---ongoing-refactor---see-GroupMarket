package com.snowtouch.hiddenharbor.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.snowtouch.hiddenharbor.R

@Composable
fun TopBar(
    title: String? = null,
    caNavigateBack: Boolean = false,
    navController: NavHostController,
    searchFieldVisible: Boolean,

) {
    val textFieldColors = TextFieldDefaults.textFieldColors(
        focusedIndicatorColor = Color.Black,
        unfocusedIndicatorColor = Color.Transparent,
        cursorColor = Color.Black
    )
    CenterAlignedTopAppBar(
        modifier = Modifier.padding(top = 8.dp),
        navigationIcon = {
            if (caNavigateBack)
                IconButton(
                    onClick = { navController.popBackStack() }
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
        },
        title = {
            if (searchFieldVisible) {
                TextField(
                    value = "",
                    onValueChange = {},
                    modifier = Modifier,
                    label = { Text(text = "Search") },
                    trailingIcon = {
                        IconButton(
                            onClick = {  }
                        ) {
                            Icon(imageVector = Icons.Filled.Search, contentDescription = null)
                        } },
                    shape = MaterialTheme.shapes.small,
                    colors = textFieldColors
                )
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = title.orEmpty(),
                        modifier = Modifier,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold)
                }
            }
        },
        actions = {
            when (CurrentScreen.name) {
                AppRoute.StartScreen.name -> {
                    GroupsButton(onClick = { navController.navigate(AppRoute.GroupScreen.name) })
                }
            }
        }
    )
}

@Composable
fun GroupsButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick,
        colors = IconButtonDefaults.iconButtonColors(
            contentColor = MaterialTheme.colorScheme.primary),
        modifier = modifier
            .border(1.dp, MaterialTheme.colorScheme.primary, MaterialTheme.shapes.small)
            .size(width = 47.dp, height = 47.dp)
            .clip(shape = MaterialTheme.shapes.small)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painterResource(R.drawable.baseline_groups_24),
                contentDescription = null
            )
            Text(text = "Groups", fontSize = 12.sp, fontWeight = FontWeight.Bold)
        }
    }
}
@Preview
@Composable
fun MainTopBarPreview(){
    val navController = NavHostController(LocalContext.current)
    TopBar(navController = navController, searchFieldVisible = true)
}