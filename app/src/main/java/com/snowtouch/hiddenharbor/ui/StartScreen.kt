package com.snowtouch.hiddenharbor.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.snowtouch.hiddenharbor.R
import com.snowtouch.hiddenharbor.data.model.bottomBarItems
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
    Row(modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TopAppBar(
            modifier = modifier
                .padding(12.dp)
                .clip(MaterialTheme.shapes.small),
            title = {
                TextField(
                    value = "",
                    onValueChange = {},
                    modifier = modifier,
                    label = { Text(text = "Search") },
                    trailingIcon = {
                        IconButton(
                            onClick = {/*TODO*/ }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Search,
                                contentDescription = null
                            )
                        }
                    },
                    shape = MaterialTheme.shapes.small,
                    colors = textFieldColors
                )

            },
            actions = { GroupsButton(onClick = { /*TODO*/ }) }
        )

    }
}
@Composable
fun GroupsButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
            .border(1.dp, Color.Black, MaterialTheme.shapes.small)
            .size(width = 50.dp, height = 50.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painterResource(R.drawable.baseline_groups_24),
                contentDescription = null
            )
            Text(text = "Groups", fontSize = 12.sp)
        }
    }
}
@Composable
fun ApplicationBottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = navBackStackEntry?.destination?.route

    BottomAppBar(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(2.dp)
    ) {
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            bottomBarItems.forEach { bottomBarItem ->
                IconButtonWithText(
                    icon = bottomBarItem.icon,
                    label = bottomBarItem.label,
                    onClick = { bottomBarItem.route?.let { navController.navigate(bottomBarItem.route) } },
                    isActive = currentScreen == bottomBarItem.route,
                    modifier = modifier
                )
                Spacer(modifier = modifier.padding(8.dp))
            }
        }
    }

}

@Composable
fun IconButtonWithText(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit,
    isActive: Boolean,
    modifier: Modifier
) {
    IconButton(
        onClick = onClick,
        modifier = modifier.defaultMinSize(minWidth = 65.dp),
        content = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = label,
                    modifier = modifier.alpha(if (isActive) 1f else 0.6f)
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