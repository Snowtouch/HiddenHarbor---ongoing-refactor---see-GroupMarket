package com.snowtouch.hiddenharbor.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

data class Option(val name: String, val icon: ImageVector, val route: String)

data class Category(val name: String, val options: List<Option>)

val categories = listOf(
    Category(
        name = "Your ads",
        options = listOf(
            Option(name = "Current", icon = Icons.Default.ShoppingCart, route = "current_ads"),
            Option(name = "Finished", icon = Icons.Default.Done, route = "finished_ads"),
            Option(name = "Drafts", icon = Icons.Default.Build, route = "work_ads")
        )
    ),
    Category(
        name = "Messages",
        options = listOf(
            Option(name = "Current", icon = Icons.Default.Email, route = "current_messages"),
            Option(name = "Deleted", icon = Icons.Default.Delete, route = "trash_messages")
        )
    ),
    Category(
        name = "Settings",
        options = listOf(
            Option(name = "Account data", icon = Icons.Default.Person, route = "account_data"),
            Option(name = "Theme", icon = Icons.Default.Edit, route = "theme_options"),
            Option(name = "About", icon = Icons.Default.Info, route = "about_app")
        )
    )
)