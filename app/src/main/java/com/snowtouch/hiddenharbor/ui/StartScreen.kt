package com.snowtouch.hiddenharbor.ui

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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
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
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = navBackStackEntry?.destination?.route
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
                onClick = { navController.navigate(AppRoute.StartScreen.name) },
                isActive = currentScreen == AppRoute.StartScreen.name
            )
            Spacer(modifier = Modifier.width(8.dp))
            IconButtonWithText(
                icon = Icons.Filled.Favorite,
                label = "Favorites",
                onClick = {  },
                isActive = false
            )
            Spacer(modifier = Modifier.width(8.dp))
            IconButtonWithText(
                icon = Icons.Filled.AddCircle,
                label = "Add",
                onClick = {  },
                isActive = false
            )
            Spacer(modifier = Modifier.width(8.dp))
            IconButtonWithText(
                icon = Icons.Filled.Email,
                label = "Messages",
                onClick = {  },
                isActive = false
            )
            Spacer(modifier = Modifier.width(8.dp))
            IconButtonWithText(
                icon = Icons.Filled.Person,
                label = "Account",
                onClick = { navController.navigate(AppRoute.AccountScreen.name) },
                isActive = currentScreen == AppRoute.AccountScreen.name
            )
        }
    }
}
@Composable
fun IconButtonWithText(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit,
    isActive: Boolean
) {
    IconButton(
        onClick = onClick,
        modifier = Modifier.defaultMinSize(minWidth = 65.dp),
        content = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = label,
                    modifier = Modifier.alpha(if (isActive) 1f else 0.6f)
                )
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