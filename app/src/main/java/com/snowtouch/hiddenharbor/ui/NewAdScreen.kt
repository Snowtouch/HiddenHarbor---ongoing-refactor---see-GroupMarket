package com.snowtouch.hiddenharbor.ui

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.snowtouch.hiddenharbor.data.model.Ad
import com.snowtouch.hiddenharbor.data.model.adCategories
import com.snowtouch.hiddenharbor.ui.components.TopBar
import com.snowtouch.hiddenharbor.ui.components.UserNotLoggedScreenContent
import com.snowtouch.hiddenharbor.viewmodel.NewAdScreenViewModel
import com.snowtouch.hiddenharbor.viewmodel.UserState

@Composable
fun NewAdScreen(
    navController: NavHostController,
    viewModel: NewAdScreenViewModel
) {
    val user by viewModel.user.collectAsState()
    val userLoggedIn by viewModel.userLoggedIn.collectAsState()

    val context = LocalContext.current

    Scaffold(
        modifier = Modifier,
        topBar = {
            TopBar(caNavigateBack = true, navController = navController, searchFieldVisible = false) },

    ) { innerPadding ->
        if (!userLoggedIn) { //TEST USER VARIABLE - TO REMOVE
            val scrollState = rememberScrollState()
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(8.dp)
                    .verticalScroll(scrollState)
                    .sizeIn(maxWidth = 370.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
                ) {
                AdTextField(
                    modifier = Modifier,
                    text = "Title",
                    value = "",
                    onValueChange = {})
                AdTextField(
                    modifier = Modifier
                        .sizeIn(minHeight = 200.dp),
                    text = "Description",
                    value = "",
                    onValueChange = {})

                CategoryMenu(label = "Select category", subcategory = false, context = context, viewModel = viewModel)
                if (viewModel.adUiState.ad.category != null) {
                    CategoryMenu(label = "Select subcategory", subcategory = true, context = context, viewModel = viewModel)
                }
                AdTextField(
                    modifier = Modifier, 
                    text = "Price", 
                    value = "", 
                    onValueChange = {})
                Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                    var selected by remember { mutableStateOf(false) }
                    FilterChip(
                        onClick = { selected = !selected },
                        label = {
                            Text("Filter chip")
                        },
                        selected = selected,
                        leadingIcon = if (selected) {
                            {
                                Icon(
                                    imageVector = Icons.Filled.Done,
                                    contentDescription = "Done icon",
                                    modifier = Modifier.size(FilterChipDefaults.IconSize)
                                )
                            }
                        } else {
                            null
                        },
                    )
                }
            }
        } else {
            UserNotLoggedScreenContent(
                paddingValues = innerPadding,
                navController = navController
            )
        }
    }
}
@Composable
fun CategoryMenu(
    label: String,
    subcategory: Boolean,
    context: Context,
    viewModel: NewAdScreenViewModel
) {
    val adUiState = viewModel.adUiState

    var expanded by remember { mutableStateOf(false) }

    val selectedCategoryName = if (!subcategory) {
        adCategories.getOrNull(adUiState.ad.category ?: 0)?.categoryName?.let {
            context.resources.getString(it)
        } ?: ""
    } else {
        adUiState.ad.subcategory?.let {
            context.resources.getString(adCategories[adUiState.ad.category ?: 0].adSubcategoryList[it])
        } ?: ""
    }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it },
        modifier = Modifier.padding(bottom = 6.dp)
    ) {
        TextField(
            value = selectedCategoryName,
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp)
                .menuAnchor(),
            readOnly = true,
            label = { Text(text = label) }
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            if (!subcategory) {
                adCategories.forEachIndexed { index, category ->
                    DropdownMenuItem(
                        text = { Text(context.resources.getString(category.categoryName)) },
                        onClick = {
                            viewModel.updateAdUiState(updatedAd = Ad(category = index))
                            expanded = false
                        },
                    )
                }
            }
            if (subcategory) {
                adCategories[adUiState.ad.category ?: 0].adSubcategoryList.forEachIndexed { index, subcategoryNameId ->
                    val subcategoryName = context.resources.getString(subcategoryNameId)
                    DropdownMenuItem(
                        text = { Text(subcategoryName) },
                        onClick = {
                            viewModel.updateAdUiState(
                                viewModel.adUiState.ad.copy(subcategory = index)
                            )
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}


@Composable
fun AdTextField(
    modifier: Modifier,
    text: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    Card(modifier.padding(bottom = 6.dp)){
        Text(
            text = text,
            modifier = Modifier.padding(start = 12.dp, bottom = 4.dp),
            style = MaterialTheme.typography.titleMedium
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = modifier
                .fillMaxWidth()
                .padding(start = 12.dp, end = 12.dp, bottom = 12.dp),
            textStyle = MaterialTheme.typography.bodyMedium
        )
    }
}
@Preview
@Composable
fun NewAdScreenPreview(){
    val navController = NavHostController(LocalContext.current)
    val viewModel =  NewAdScreenViewModel(UserState)
    NewAdScreen(navController = navController, viewModel = viewModel)
}