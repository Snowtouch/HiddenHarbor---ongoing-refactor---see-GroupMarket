package com.snowtouch.hiddenharbor.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.snowtouch.hiddenharbor.R
import com.snowtouch.hiddenharbor.model.Category
import com.snowtouch.hiddenharbor.model.Option
import com.snowtouch.hiddenharbor.viewmodel.LoginScreenViewModel

@Composable
fun AccountScreen(
    categories: List<Category>,
    navController: NavHostController,
    viewModel: LoginScreenViewModel,
) {
    val uiState by viewModel.uiState
    val context = LocalContext.current
    val appState by viewModel.appState

    Scaffold(
        bottomBar = { ApplicationBottomBar(navController) }
    ) { innerPadding ->
        if (appState.userLoggedIn)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.background)
                    .padding(innerPadding)
                    .padding(24.dp)
            ) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    ElevatedButton(
                        onClick = { viewModel.signOut()
                        //navController.navigate(route = AppRoute.AccountScreen.name)
                        }
                    ) {
                        Text(text = "Sign out")
                    }
                }
                categories.forEach { category ->
                    CategoryItem(category = category)
                    category.options.forEach { option ->
                        OptionItem(option = option)
                    }
                }
            }
        else
        {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Column(
                    modifier = Modifier.weight(0.5f)
                ) {
                    LoginBox(
                        uiState.email,
                        viewModel::onEmailChange,
                        uiState.password,
                        viewModel::onPasswordChange
                    )
                }
                ElevatedButton(
                    modifier = Modifier.size(width = 175.dp, height = 50.dp),
                    onClick = {
                        viewModel.signIn(uiState.email, uiState.password, context)
                              },
                    shape = MaterialTheme.shapes.small,
                    colors = ButtonDefaults.buttonColors(),
                    elevation = ButtonDefaults.buttonElevation()
                ) {
                    Text(
                        text = "Login",
                        color = Color.White)
                }
                Spacer(modifier = Modifier.padding(top = 8.dp))
                ElevatedButton(
                    modifier = Modifier.size(width = 175.dp, height = 50.dp),
                    onClick = { viewModel.createAccount(uiState.email, uiState.password, context)
                        //navController.navigate(route = AppRoute.AccountScreen.name)
                              },
                    shape = MaterialTheme.shapes.small,
                    colors = ButtonDefaults.buttonColors(),
                    elevation = ButtonDefaults.buttonElevation()
                ) {
                    Text(
                        text = "Register",
                        color = Color.White)
                }
                Column(
                    modifier = Modifier
                        .background(color = MaterialTheme.colorScheme.background)
                        .padding(innerPadding)
                        .padding(24.dp)
                        .weight(1f)
                ) {
                    CategoryItem(category = categories.last())
                    val filteredOptions = categories.last().options.filterNot {
                        option -> option.name == "Account data"
                    }
                    filteredOptions.forEach() { option ->
                        OptionItem(option = option)
                    }
                }
            }
        }
    }
}
@Composable
fun LoginBox(
    valueEmail: String,
    onNewValueEmail: (String) -> Unit,
    valuePassword: String,
    onNewValuePassword: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val showPassword = remember { mutableStateOf(false) }
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ElevatedCard(
            modifier = Modifier,
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
            elevation = CardDefaults.cardElevation()
        ) {
            Column(modifier.padding(24.dp)) {
                OutlinedTextField(
                    value = valueEmail,
                    onValueChange = { onNewValueEmail(it)},
                    label = { Text(text = "E-mail") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.Email,
                            contentDescription = null
                        )
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    colors = TextFieldDefaults.outlinedTextFieldColors(containerColor = Color.White)
                )
                Spacer(modifier = modifier.height(8.dp))
                OutlinedTextField(
                    value = valuePassword,
                    onValueChange = { onNewValuePassword(it) },
                    label = { Text(text = "Password") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.Lock,
                            contentDescription = null
                        )
                    },
                    trailingIcon = {
                        IconButton(
                            onClick = { showPassword.value = !showPassword.value }
                        ) {
                            Icon(painterResource(if (showPassword.value) R.drawable.baseline_visibility_24
                            else R.drawable.baseline_visibility_off_24), contentDescription = null)

                        }

                    },
                    singleLine = true,
                    visualTransformation = if (showPassword.value) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    colors = TextFieldDefaults.outlinedTextFieldColors(containerColor = Color.White)
                )
            }
        }
    }
}
@Composable
fun CategoryItem(category: Category) {
    Row(modifier = Modifier.padding(top = 12.dp)) {
        Text(text = category.name, style = MaterialTheme.typography.headlineLarge)
    }
}
@Composable
fun OptionItem(option: Option) {
    Row(
        modifier = Modifier
            .padding(start = 12.dp)
            .fillMaxWidth()
            .size(height = 48.dp, width = 300.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = option.icon, contentDescription = null)
        Spacer(modifier = Modifier.padding(6.dp))
        Text(text = option.name, style = MaterialTheme.typography.titleMedium)
    }
    Divider()
}
/*@Preview
@Composable
fun AccountScreenPreview() {
    val loginScreenViewModel: LoginScreenViewModel
    AccountScreen(
        categories,
        navController = NavHostController(LocalContext.current),
        viewModel = loginScreenViewModel
    )
}*/