package com.snowtouch.hiddenharbor.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.snowtouch.hiddenharbor.R
import com.snowtouch.hiddenharbor.data.model.AccountCategoryOption
import com.snowtouch.hiddenharbor.data.model.AccountScreenCategory
import com.snowtouch.hiddenharbor.data.model.accountScreenCategories
import com.snowtouch.hiddenharbor.ui.components.ApplicationBottomBar
import com.snowtouch.hiddenharbor.viewmodel.AccountScreenViewModel

@Composable
fun AccountScreen(
    categories: List<AccountScreenCategory>,
    navController: NavHostController,
    viewModel: AccountScreenViewModel,
) {
    val uiState by viewModel.uiState
    val context = LocalContext.current
    val user by viewModel.user.collectAsState()
    val userLoggedIn by viewModel.userLoggedIn.collectAsState()
    val scrollState = rememberScrollState()
    Scaffold(
        modifier = Modifier,
        bottomBar = { ApplicationBottomBar(navController) }
    ) { innerPadding ->
        if (userLoggedIn)
            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .fillMaxWidth()
                    .padding(innerPadding)
                    .padding(24.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    ElevatedButton(
                        onClick = { viewModel.signOut(context) },
                        modifier = Modifier.size(width = 100.dp, height = 50.dp),
                        shape = MaterialTheme.shapes.small,
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
                    ) {
                        Text(
                            text = "Logout",
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                categories.forEach { category ->
                    CategoryItem(accountScreenCategory = category)
                    category.accountCategoryOptions?.forEach { option ->
                        OptionItem(accountCategoryOption = option)
                    }
                }
            }
        else
        {
            Column(
                modifier = Modifier.verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LoginBox(
                    uiState.email,
                    viewModel::onEmailChange,
                    uiState.password,
                    viewModel::onPasswordChange
                )
                ElevatedButton(
                    modifier = Modifier.size(width = 175.dp, height = 50.dp),
                    onClick = { viewModel.login(uiState.email, uiState.password, context) },
                    shape = MaterialTheme.shapes.small,
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
                ) {
                    Text(
                        text = "Login",
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.padding(top = 8.dp))
                ElevatedButton(
                    modifier = Modifier.size(width = 175.dp, height = 50.dp),
                    onClick = { viewModel.createAccount(uiState.email, uiState.password, context) },
                    shape = MaterialTheme.shapes.small,
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
                ) {
                    Text(
                        text = "Sign up",
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontWeight = FontWeight.Bold
                    )
                }
                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .padding(24.dp)
                ) {
                    CategoryItem(accountScreenCategory = categories.last())
                    val filteredOptions = categories.last().accountCategoryOptions?.filterNot { option ->
                        option.name == "Account data" }
                    filteredOptions?.forEach { option ->
                        OptionItem(accountCategoryOption = option)
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
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp, bottom = 16.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ElevatedCard(
            modifier = Modifier,
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
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
                    colors = TextFieldDefaults.textFieldColors(containerColor = MaterialTheme.colorScheme.background)
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
                    colors = TextFieldDefaults.textFieldColors(containerColor = MaterialTheme.colorScheme.background)
                )
            }
        }
    }
}
@Composable
fun CategoryItem(accountScreenCategory: AccountScreenCategory) {
    Row(modifier = Modifier.padding(top = 12.dp)) {
        Text(text = accountScreenCategory.name, style = MaterialTheme.typography.headlineLarge)
    }
}
@Composable
fun OptionItem(accountCategoryOption: AccountCategoryOption) {
    Row(
        modifier = Modifier
            .padding(start = 12.dp)
            .fillMaxWidth()
            .size(height = 48.dp, width = 300.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (accountCategoryOption.icon != null) {Icon(imageVector = accountCategoryOption.icon, contentDescription = null)}
        Spacer(modifier = Modifier.padding(6.dp))
        Text(text = accountCategoryOption.name, style = MaterialTheme.typography.titleMedium)
    }
    Divider()
}
@Preview
@Composable
fun AccountScreenPreview() {
    val accountScreenViewModel: AccountScreenViewModel = viewModel()
    AccountScreen( accountScreenCategories,
        navController = NavHostController(LocalContext.current),
        viewModel = accountScreenViewModel
    )
}