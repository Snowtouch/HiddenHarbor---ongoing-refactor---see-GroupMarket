package com.snowtouch.hiddenharbor.data.model

import androidx.compose.ui.graphics.vector.ImageVector

data class Option(
    val name: String,
    val icon: ImageVector?,
    val route: String
)
data class Category(
    val name: String,
    val options: List<Option>?
)