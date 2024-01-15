package com.snowtouch.hiddenharbor.ui.components

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CustomElevatedCard(
    modifier: Modifier = Modifier,
    content: @Composable() (ColumnScope.() -> Unit)
) {
    ElevatedCard(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        content = content)
}