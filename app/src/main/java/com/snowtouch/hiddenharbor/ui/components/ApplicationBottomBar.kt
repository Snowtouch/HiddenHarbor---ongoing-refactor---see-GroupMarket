package com.snowtouch.hiddenharbor.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.snowtouch.hiddenharbor.data.model.bottomBarItems

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
                    modifier = modifier.alpha(if (isActive) 1f else 0.4f),
                    tint = if (isActive) MaterialTheme.colorScheme.primary else Color.Gray
                )
                Text(text = label, fontSize = 12.sp)
            }
        }
    )
}