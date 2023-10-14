package com.snowtouch.hiddenharbor.ui

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.snowtouch.hiddenharbor.R
import com.snowtouch.hiddenharbor.data.model.Ad
import com.snowtouch.hiddenharbor.data.model.adCategories
import com.snowtouch.hiddenharbor.ui.components.CustomElevatedCard
import com.snowtouch.hiddenharbor.ui.components.SnackbarGlobalDelegate
import com.snowtouch.hiddenharbor.ui.components.TopBar
import com.snowtouch.hiddenharbor.ui.components.UserNotLoggedScreenContent
import com.snowtouch.hiddenharbor.viewmodel.NewAdScreenViewModel
import com.snowtouch.hiddenharbor.viewmodel.UserState
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Composable
fun NewAdScreen(
    navController: NavHostController,
    viewModel: NewAdScreenViewModel = koinViewModel()
) {
    val user by viewModel.user.collectAsState()
    val userLoggedIn by viewModel.userLoggedIn.collectAsState()
    val snackbarGlobalDelegate = koinInject<SnackbarGlobalDelegate>()

    val context = LocalContext.current

    val scope = rememberCoroutineScope()
    var groupButtonEnabled by remember { mutableStateOf(false)}
    var expandedGroupMenu by remember { mutableStateOf(false) }
    var selectedGroup by remember { mutableStateOf(String()) }

    Scaffold(
        modifier = Modifier,
        topBar = {
            TopBar(title = "Add advertisement", caNavigateBack = true, navController = navController, searchFieldVisible = false) },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarGlobalDelegate.snackbarHostState) {
            val backgroundColor = snackbarGlobalDelegate.snackbarBackgroundColor
            Snackbar(snackbarData = it, containerColor = backgroundColor)
            }
        }

    ) { innerPadding ->
        if (!userLoggedIn) { //TEST USER VARIABLE - TO REMOVE
            val scrollState = rememberScrollState()
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(8.dp)
                    .verticalScroll(scrollState)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
                ) {

                AdImagePicker(viewModel = viewModel)

                AdTextField(
                    modifier = Modifier,
                    text = "Title",
                    value = viewModel.adUiState.ad.title,
                    onValueChange = { viewModel.updateAdUiState(viewModel.adUiState.ad.copy(title = it)) }
                )
                AdTextField(
                    modifier = Modifier
                        .sizeIn(minHeight = 200.dp),
                    text = "Description",
                    value = viewModel.adUiState.ad.description,
                    onValueChange = { viewModel.updateAdUiState(viewModel.adUiState.ad.copy(description = it)) }
                )
                CustomElevatedCard(modifier = Modifier.padding(top = 4.dp, bottom = 4.dp)) {
                    CategoryMenu(
                        label = "Select category",
                        subcategory = false,
                        context = context, viewModel =
                        viewModel
                    )

                    AnimatedVisibility(
                        visible = viewModel.adUiState.ad.category != null,
                        enter = scaleIn(),
                        exit = scaleOut()
                    ) {
                        CategoryMenu(
                            label = "Select subcategory",
                            subcategory = true,
                            context = context,
                            viewModel = viewModel
                        )
                    }
                }
                AdTextField(
                    modifier = Modifier,
                    text = "Price", /*TODO- crash on comma*/
                    value = Ad(price = viewModel.adUiState.ad.price).getFormattedPrice(),
                    onValueChange = { viewModel.updateAdUiState(viewModel.adUiState.ad.copy(price = it.toDouble())) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                    FilterChip(
                        onClick = { groupButtonEnabled = !groupButtonEnabled
                            scope.launch {
                                scrollState.animateScrollBy(200f)
                            }
                                  },
                        label = {
                            Text("Group ad")
                        },
                        selected = groupButtonEnabled,
                        leadingIcon = if (groupButtonEnabled)
                        { {
                            Icon(
                                imageVector = Icons.Filled.Done,
                                contentDescription = "Done icon",
                                modifier = Modifier.size(FilterChipDefaults.IconSize)) }
                        } else {
                            null
                        },
                    )
                AnimatedVisibility(
                    visible = groupButtonEnabled,
                    enter = expandVertically(),
                    exit = shrinkVertically()
                ) {
                    CustomElevatedCard{
                        ExposedDropdownMenuBox(
                            expanded = expandedGroupMenu,
                            onExpandedChange = { expandedGroupMenu = it }
                        ) {
                            OutlinedTextField(
                                value = selectedGroup,
                                onValueChange = {},
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 12.dp, end = 12.dp, bottom = 6.dp),
                                readOnly = true,
                                label = {
                                    Text(
                                        text = if (user.groups.isEmpty()) "No groups available"
                                        else "Select group"
                                    )
                                },
                                colors = TextFieldDefaults.textFieldColors(
                                    containerColor = MaterialTheme.colorScheme.background)
                            )
                            ExposedDropdownMenu(
                                expanded = expandedGroupMenu,
                                onDismissRequest = { expandedGroupMenu = false }
                            ) {
                                if (user.groups.isNotEmpty())
                                    user.groups.forEach {
                                        DropdownMenuItem(
                                            text = { Text(text = it!!) },
                                            onClick = { selectedGroup = it!! }
                                        )
                                    }
                            }
                        }
                    }
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
fun AdImagePicker(
    viewModel: NewAdScreenViewModel
) {
    var selectedImageUris by remember { mutableStateOf<List<Uri>>(emptyList()) }
    val multiplePhotoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(maxItems = 6)
    ) {
        if (it != null) {
            Log.d("PhotoPicker", "Selected URI: $it")
            selectedImageUris = it
            viewModel.updateAdUiState(viewModel.adUiState.ad.copy(photoUrls = selectedImageUris))
        } else {
            Log.d("PhotoPicker", "No media selected")
        }
    }

    CustomElevatedCard(modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 8.dp)
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)){
            AsyncImage(
                modifier = Modifier.fillMaxWidth()
                    .clip(MaterialTheme.shapes.small)
                    .clickable {
                        multiplePhotoPicker.launch(
                            PickVisualMediaRequest(
                                ActivityResultContracts
                                    .PickVisualMedia
                                    .ImageOnly
                            )
                        )
                    },
                model = if (selectedImageUris.isNotEmpty()) {
                    ImageRequest
                        .Builder(LocalContext.current)
                        .data(selectedImageUris[0])
                        .build()
                } else {
                    ImageRequest
                        .Builder(LocalContext.current)
                        .data(R.drawable.no_image)
                        .build()
                },
                contentDescription = "Ad main image",
                alignment = Alignment.Center
            )
        }
        AnimatedVisibility(
            visible = selectedImageUris.isNotEmpty(),
            enter = slideInVertically(),
            exit = slideOutVertically()
        ) {

            Divider()
            LazyRow(
                modifier = Modifier.height(100.dp),
                contentPadding = PaddingValues(4.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                items(selectedImageUris) {
                    AsyncImage(
                        model = ImageRequest
                            .Builder(LocalContext.current)
                            .data(it)
                            .build(),
                        contentDescription = null,
                        modifier = Modifier,
                        contentScale = ContentScale.Fit)
                    Spacer(modifier = Modifier.padding(2.dp))
                }
            }}
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
        OutlinedTextField(
            value = selectedCategoryName,
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, end = 12.dp)
                .menuAnchor(),
            readOnly = true,
            label = { Text(text = label) },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = MaterialTheme.colorScheme.background)
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            when (subcategory) {
                false ->
                    adCategories.forEachIndexed { index, category ->
                        DropdownMenuItem(
                            text = { Text(context.resources.getString(category.categoryName)) },
                            onClick = {
                                viewModel.updateAdUiState(
                                viewModel.adUiState.ad.copy(category = index, subcategory = null))
                                expanded = false
                                      },
                            )
                    }
                true ->
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
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions()
) {
    CustomElevatedCard(modifier.padding(top = 4.dp, bottom = 4.dp)) {
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
            textStyle = MaterialTheme.typography.bodyMedium,
            colors = TextFieldDefaults.textFieldColors(containerColor = MaterialTheme.colorScheme.background),
            keyboardOptions = keyboardOptions
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