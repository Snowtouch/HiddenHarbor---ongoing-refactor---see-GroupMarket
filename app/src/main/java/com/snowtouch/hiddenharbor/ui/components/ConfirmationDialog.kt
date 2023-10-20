package com.snowtouch.hiddenharbor.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ConfirmationDialog(
    onConfirmButton: (Boolean) -> Unit,
    onDismissButton: () -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    icon: Int,
    titleText: () -> String,
    ) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
                        UniversalButton(onClick = { onConfirmButton(true) }, text = "Confirm")
        }  ,
        modifier = modifier,
        dismissButton = {
                        UniversalButton(onClick = { onDismissButton() }, text = "Cancel")
        },
        icon = {
            Box(modifier = modifier.border(1.dp, Color.Black.copy(0.7f), shapes.extraSmall)) {
                Icon(
                    painterResource(icon),
                    contentDescription = null,
                    modifier = modifier
                        .padding(6.dp)
                        .size(30.dp)
                )
            }
        },
        title = { Text(
            text = titleText(),
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.Normal,
            fontSize = 18.sp
        ) },
        text = null,
        shape = shapes.small

    )
}
/*
@Preview
@Composable
fun ConfirmationDialogPreview(){
    ConfirmationDialog(
        onConfirmButton = { /*TODO*/ },
        onDismissButton = { /*TODO*/ },
        onDismissRequest = { /*TODO*/ },
        titleText = "",
        icon = R.drawable.baseline_question_mark_24
    )
}*/