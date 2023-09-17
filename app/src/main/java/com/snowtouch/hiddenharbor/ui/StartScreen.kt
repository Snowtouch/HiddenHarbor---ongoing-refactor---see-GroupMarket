package com.snowtouch.hiddenharbor.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.snowtouch.hiddenharbor.ui.theme.HiddenHarborTheme

@Composable
fun StartScreen(navController: NavHostController){
    Scaffold(
        topBar = { StartScreenTopBar() },
        bottomBar = { ApplicationBottomBar(navController) }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
        }
    }
}
@Composable
fun StartScreenTopBar(
    modifier: Modifier = Modifier
){
    val textFieldColors = TextFieldDefaults.textFieldColors(
        containerColor = Color.Transparent,
        focusedIndicatorColor = Color.Black,
        unfocusedIndicatorColor = Color.Transparent,
        cursorColor = Color.Black
    )
    CenterAlignedTopAppBar(
        modifier = modifier
            .padding(8.dp)
            .clip(MaterialTheme.shapes.small),
        title = {
            TextField(
                value = "",
                onValueChange = {},
                modifier = Modifier
                    .fillMaxWidth(),
                label = { Text(text = "Search") },
                trailingIcon = {
                    IconButton(
                        onClick = {/*TODO*/ }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = null) } },
                shape = MaterialTheme.shapes.small,
                colors = textFieldColors
            )
        },
    )
}
@Composable
fun ApplicationBottomBar(navController: NavHostController) {
    BottomAppBar(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(2.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButtonWithText(
                icon = Icons.Filled.Home,
                label = "Home",
                onClick = { navController.navigate(AppRoute.StartScreen.name) }
            )
            Spacer(modifier = Modifier.width(8.dp)) // Dodaj odstęp
            IconButtonWithText(
                icon = Icons.Filled.Favorite,
                label = "Favorites",
                onClick = { /* Handle click */ }
            )
            Spacer(modifier = Modifier.width(8.dp)) // Dodaj odstęp
            IconButtonWithText(
                icon = Icons.Filled.AddCircle,
                label = "Add",
                onClick = { /* Handle click */ }
            )
            Spacer(modifier = Modifier.width(8.dp)) // Dodaj odstęp
            IconButtonWithText(
                icon = Icons.Filled.Email,
                label = "Messages",
                onClick = { /* Handle click */ }
            )
            Spacer(modifier = Modifier.width(8.dp)) // Dodaj odstęp
            IconButtonWithText(
                icon = Icons.Filled.Person,
                label = "Account",
                onClick = { navController.navigate(AppRoute.AccountScreen.name) }
            )
        }
    }
}

@Composable
fun IconButtonWithText(icon: ImageVector, label: String, onClick: () -> Unit) {
    IconButton(
        onClick = onClick,
        modifier = Modifier.defaultMinSize(minWidth = 65.dp),
        content = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
            ) {
                Icon(imageVector = icon, contentDescription = label)
                Text(text = label, fontSize = 12.sp)
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun StartScreenPreview() {
    HiddenHarborTheme {
        val context = LocalContext.current
        StartScreen(navController = NavHostController(context))
    }
}