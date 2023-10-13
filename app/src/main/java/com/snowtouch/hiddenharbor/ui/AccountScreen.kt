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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
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
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import com.snowtouch.hiddenharbor.R
import com.snowtouch.hiddenharbor.data.model.AccountCategoryOption
import com.snowtouch.hiddenharbor.data.model.AccountScreenCategory
import com.snowtouch.hiddenharbor.data.model.accountScreenCategories
import com.snowtouch.hiddenharbor.ui.components.ApplicationBottomBar
import com.snowtouch.hiddenharbor.ui.components.CustomElevatedCard
import com.snowtouch.hiddenharbor.ui.components.SnackbarGlobalDelegate
import com.snowtouch.hiddenharbor.viewmodel.AccountActions
import com.snowtouch.hiddenharbor.viewmodel.AccountScreenViewModel
import com.snowtouch.hiddenharbor.viewmodel.LoginUiState
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Composable
fun AccountScreen(
    categories: List<AccountScreenCategory>,
    navController: NavHostController,
    viewModel: AccountScreenViewModel
) {
    val snackbarGlobalDelegate = koinInject<SnackbarGlobalDelegate>()
    val uiState by viewModel.uiState
    val userLoggedIn by viewModel.userLoggedIn.collectAsState()

    AccountScreenContent(
        categories = categories,
        navController = navController,
        snackbarGlobalDelegate = snackbarGlobalDelegate,
        uiState = uiState,
        userLoggedIn = userLoggedIn,
        accountActions = viewModel.accountActions)
}
@Composable
fun AccountScreenContent(
    categories: List<AccountScreenCategory>,
    navController: NavHostController,
    snackbarGlobalDelegate: SnackbarGlobalDelegate,
    uiState: LoginUiState,
    userLoggedIn: Boolean,
    accountActions: AccountActions
) {

    val scrollState = rememberScrollState()
    var showCreateAccountPopup = remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier,
        bottomBar = { ApplicationBottomBar(navController) },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarGlobalDelegate.snackbarHostState) {
                val backgroundColor = snackbarGlobalDelegate.snackbarBackgroundColor
                Snackbar(snackbarData = it, containerColor = backgroundColor)
            }
        }
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
                        onClick = { accountActions.signOut(snackbarGlobalDelegate) },
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
                    uiState.emailLogin,
                    onNewValueEmail = {
                            newValue -> accountActions.onEmailChange(newValue) },
                    uiState.passwordLogin,
                    onNewValuePassword = {
                            newValue -> accountActions.onPasswordChange(newValue) }
                )
                AccountScreenButton(
                    onClick = {
                        accountActions.login(
                            uiState.emailLogin, uiState.passwordLogin, snackbarGlobalDelegate) } ,
                    text = "Login"
                )
                Text(
                    text = "Don't have account ?",
                    modifier = Modifier.padding(top = 24.dp),
                    fontWeight = FontWeight.Medium)
                AccountScreenButton(
                    onClick = { showCreateAccountPopup.value = true },
                    text = "Create"
                )
                if (showCreateAccountPopup.value)
                    CreateAccountPopUp(
                        valueEmail = uiState.emailNewAccount,
                        onNewValueEmail = {
                                newValue -> accountActions.onEmailChange(newValue, true) },
                        valuePassword = uiState.passwordNewAccount,
                        onNewValuePassword = {
                                newValue -> accountActions.onPasswordChange(newValue, true) },
                        valueConfirmPassword = uiState.passwordCheck,
                        onNewValueConfirmPassword = { accountActions.onPasswordCheckChange(it) },
                        onDismissRequest = { showCreateAccountPopup.value = false }
                )
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
private fun CreateAccountPopUp(
    valueEmail: String,
    onNewValueEmail: (String) -> Unit,
    valuePassword: String,
    onNewValuePassword: (String) -> Unit,
    valueConfirmPassword: String,
    onNewValueConfirmPassword: (String) -> Unit,
    onDismissRequest: () -> Unit
) {
    Dialog(
        onDismissRequest = { onDismissRequest() },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = false
        )
    ) {
        CustomElevatedCard {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth()
            ) {
                EmailTextField(valueEmail, onNewValueEmail)
                Spacer(modifier = Modifier.padding(8.dp))
                PasswordTextField(
                    "Password", valuePassword, onNewValuePassword)
                Spacer(modifier = Modifier.padding(8.dp))
                PasswordTextField(
                    "Repeat password", valueConfirmPassword, onNewValueConfirmPassword)
            }
        }
    }
}
@Composable
private fun AccountScreenButton(
    onClick: () -> Unit,
    text: String
){
    ElevatedButton(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 25.dp),
        onClick = onClick,
        shape = MaterialTheme.shapes.extraSmall,
        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
    ) {
        Text(
            text = text,
            color = MaterialTheme.colorScheme.onPrimary,
            fontWeight = FontWeight.Bold
        )
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
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp, bottom = 8.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomElevatedCard {
            Column(modifier.padding(24.dp)) {
                EmailTextField(valueEmail, onNewValueEmail)
                Spacer(modifier = modifier.height(8.dp))
                PasswordTextField("Password", valuePassword, onNewValuePassword)
            }
        }
    }
}
@Composable
fun EmailTextField(
    valueEmail: String,
    onNewValueEmail: (String) -> Unit,
) {
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
}
@Composable
fun PasswordTextField(
    label: String,
    valuePassword: String,
    onNewValuePassword: (String) -> Unit
) {
    val showPassword = remember { mutableStateOf(false) }

    OutlinedTextField(
        value = valuePassword,
        onValueChange = { onNewValuePassword(it) },
        label = { Text(text = label) },
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
fun AccountCardPreview() {
    LoginBox(valueEmail = "", onNewValueEmail = {}, valuePassword = "", onNewValuePassword = {})
}
@Preview
@Composable
fun AccountScreenContentPreview() {
    val viewModel: AccountScreenViewModel = koinViewModel()
    val context = LocalContext.current
    val snackbarGlobalDelegate = koinInject<SnackbarGlobalDelegate>()
    AccountScreenContent(accountScreenCategories,
        NavHostController(context),
        snackbarGlobalDelegate,
        viewModel.uiState.value,
        true
        ,AccountActions(viewModel))
}