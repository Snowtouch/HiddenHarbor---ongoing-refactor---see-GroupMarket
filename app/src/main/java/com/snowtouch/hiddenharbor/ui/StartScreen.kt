package com.snowtouch.hiddenharbor.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.snowtouch.hiddenharbor.ui.theme.HiddenHarborTheme

@Composable
fun StartScreen(){
    Scaffold(
        topBar = { StartScreenTopBar() }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {

        }
    }
}
@Composable
fun StartScreenTopBar(
    modifier: Modifier = Modifier
){
    CenterAlignedTopAppBar(
        modifier = modifier.background(MaterialTheme.colorScheme.background),
        title = {
            Box(
                modifier = modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 8.dp), // Dodaj odpowiedni margines poziomy i dolny
                    label = { Text(text = "Search") },
                    trailingIcon = { Icon(imageVector = Icons.Filled.Search, contentDescription = null) },
                )
            }
        },
    )
}

@Preview(showBackground = true)
@Composable
fun StartScreenPreview() {
    HiddenHarborTheme {
        StartScreen()
    }
}