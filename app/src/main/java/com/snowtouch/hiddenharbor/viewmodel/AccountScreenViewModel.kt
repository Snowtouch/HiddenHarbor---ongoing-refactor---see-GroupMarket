package com.snowtouch.hiddenharbor.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.snowtouch.hiddenharbor.data.model.User
import com.snowtouch.hiddenharbor.data.repository.AccountServiceImpl
import com.snowtouch.hiddenharbor.data.repository.RealtimeDatabaseServiceImpl
import com.snowtouch.hiddenharbor.ui.components.SnackbarGlobalDelegate
import com.snowtouch.hiddenharbor.ui.components.SnackbarState
import kotlinx.coroutines.flow.StateFlow

class AccountScreenViewModel(
    private val userState: UserState,
    private val accountServiceImpl: AccountServiceImpl,
    private val realtimeDatabaseServiceImpl: RealtimeDatabaseServiceImpl
) : ViewModel() {
    var uiState = mutableStateOf(LoginUiState())
        private set
    val user: StateFlow<User> = userState.user
    val userLoggedIn: StateFlow<Boolean> = userState.userLoggedIn
    val accountActions = AccountActions(this)

    fun onEmailChange(newValue: String, newAccount: Boolean) {
        if (!newAccount) uiState.value = uiState.value.copy(emailLogin = newValue)
        else uiState.value = uiState.value.copy(emailNewAccount = newValue)
    }
    fun onPasswordChange(newValue: String, newAccount: Boolean) {
        if (!newAccount) uiState.value = uiState.value.copy(passwordLogin = newValue)
        else uiState.value = uiState.value.copy(passwordNewAccount = newValue)
    }
    fun onPasswordCheckChange(newValue: String) {
        uiState.value = uiState.value.copy(passwordCheck = newValue)
    }

    fun createAccount(email: String, password: String, passwordCheck: String, snackbarDelegate: SnackbarGlobalDelegate) {
        if (checkCredentials(email, password, snackbarDelegate, passwordCheck)) return
        else {
            accountServiceImpl.createAccount(email, password) { uid, exception ->
                if (exception!=null) {
                    handleExceptions(exception, snackbarDelegate)
                } else if(uid!=null) {
                    createUserDataOnAccountCreation(uid, email, snackbarDelegate)
                    handleSuccessfulEvents("Account created", snackbarDelegate)
                }
            }
        }
    }
    fun login(email: String, password: String, snackbarDelegate: SnackbarGlobalDelegate) {
        if (checkCredentials(email, password, snackbarDelegate = snackbarDelegate)) {
            return
        }
        accountServiceImpl.authenticate(email, password) { exception ->
            if (exception != null) {
                handleExceptions(exception, snackbarDelegate)
            } else {
                userState.setUserLoggedIn(true)
                toggleCurrentUserDataListener(true, snackbarDelegate)
                handleSuccessfulEvents("Successfully logged in", snackbarDelegate)
            }
        }
    }
    fun signOut(snackbarDelegate: SnackbarGlobalDelegate) {
        accountServiceImpl.signOut()
        userState.setUserLoggedIn(false)
        toggleCurrentUserDataListener(false, snackbarDelegate)
        clearEmailAndPasswordFields()
        handleSuccessfulEvents("Successfully logged out", snackbarDelegate)
    }
    fun toggleCurrentUserDataListener(
        enable: Boolean,
        snackbarDelegate: SnackbarGlobalDelegate
    ) {
        if (enable) realtimeDatabaseServiceImpl.userDataListener(UserState) { exception ->
            handleExceptions(exception, snackbarDelegate)
        } else {
            realtimeDatabaseServiceImpl.removeUserValueEventListener(userState)
        }
    }
    private fun createUserDataOnAccountCreation(
        uid: String,
        email: String,
        snackbarDelegate: SnackbarGlobalDelegate
    ) {
        realtimeDatabaseServiceImpl.createUserData(uid, email) { exception ->
            handleExceptions(exception, snackbarDelegate)
        }
    }
    private fun clearEmailAndPasswordFields(

    ) {
        uiState.value = uiState.value.copy(emailLogin = "", passwordLogin = "",
            emailNewAccount = "", passwordNewAccount = "", passwordCheck = "")
    }
    private fun handleSuccessfulEvents(message: String, snackbarDelegate: SnackbarGlobalDelegate) {
        snackbarDelegate.showSnackbar(
            SnackbarState.DEFAULT,
            message,
            withDismissAction = true
        )
    }
    private fun handleExceptions(exception: Throwable?, snackbarDelegate: SnackbarGlobalDelegate) {
        snackbarDelegate.showSnackbar(
            SnackbarState.ERROR,
            exception?.localizedMessage ?: "",
            withDismissAction = true
        )
    }
    private fun checkCredentials(
        email: String,
        password: String,
        snackbarDelegate: SnackbarGlobalDelegate,
        passwordCheck: String? = null
    ) : Boolean {
        return when {
            (email.isEmpty() || password.isEmpty() || passwordCheck?.isNotEmpty() == true) -> {
                handleExceptions(Throwable("Enter valid credentials"), snackbarDelegate)
                true
            }
            (passwordCheck != null && password != passwordCheck) -> {
                handleExceptions(Throwable("Passwords don't match"), snackbarDelegate)
                true
            }
            else -> false
        }
    }
}

class AccountActions(
    private val viewModel: AccountScreenViewModel
) {
    fun onEmailChange(newValue: String, newAccount: Boolean = false) {
        if (!newAccount) viewModel.onEmailChange(newValue, false)
        else viewModel.onEmailChange(newValue, true)
    }
    fun onPasswordChange(newValue: String, newAccount: Boolean = false) {
        if (!newAccount) viewModel.onPasswordChange(newValue, false)
        else viewModel.onPasswordChange(newValue, true)
    }
    fun onPasswordCheckChange(newValue: String) {
        viewModel.onPasswordCheckChange(newValue)
    }
    fun createAccount(email: String, password: String, passwordCheck: String, snackbarDelegate: SnackbarGlobalDelegate) {
        viewModel.createAccount(email, password, passwordCheck, snackbarDelegate)
    }
    fun login(email: String, password: String, snackbarDelegate: SnackbarGlobalDelegate) {
        viewModel.login(email, password, snackbarDelegate)
    }
    fun signOut(snackbarDelegate: SnackbarGlobalDelegate) {
        viewModel.signOut(snackbarDelegate)
    }
}
data class LoginUiState(
    val emailLogin: String = "",
    val passwordLogin: String = "",
    val emailNewAccount: String = "",
    val passwordNewAccount: String = "",
    val passwordCheck: String = ""
)