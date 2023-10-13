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

    fun createAccount(email: String, password: String, snackbarDelegate: SnackbarGlobalDelegate) {
        if (checkCredentials(email, password)) {
            snackbarDelegate.showSnackbar(
                SnackbarState.ERROR, "Enter valid credentials", withDismissAction = true
            )
        } else {
            accountServiceImpl.createAccount(email, password) { uid, error ->
                if (error!=null) {
                    snackbarDelegate.showSnackbar(
                        SnackbarState.ERROR,
                        error.localizedMessage?.toString() ?: "", withDismissAction = true
                    )
                } else if(uid!=null) {
                    realtimeDatabaseServiceImpl.createUserData(uid, email) { exception ->
                        snackbarDelegate.showSnackbar(
                            SnackbarState.ERROR,
                            exception?.localizedMessage.toString(), withDismissAction = true
                        )
                    }
                }
            }
        }
    }
    fun login(email: String, password: String, snackbarDelegate: SnackbarGlobalDelegate) {
        if (checkCredentials(email, password)) {
            snackbarDelegate.showSnackbar(
                SnackbarState.ERROR, "Enter valid credentials", withDismissAction = true
            )
        } else {
            accountServiceImpl.authenticate(email, password) { error ->
                if (error!=null) {
                    snackbarDelegate.showSnackbar(
                        SnackbarState.ERROR, error.localizedMessage?: "", withDismissAction = true
                    )
                } else {
                    userState.setUserLoggedIn(true)
                    toggleCurrentUserDataListener(true, snackbarDelegate)
                }
            }
        }
    }
    fun signOut(snackbarDelegate: SnackbarGlobalDelegate){
        accountServiceImpl.signOut()
        userState.setUserLoggedIn(false)
        toggleCurrentUserDataListener(false, snackbarDelegate)
        clearEmailAndPasswordFields()
    }
    fun toggleCurrentUserDataListener(enable: Boolean, snackbarDelegate: SnackbarGlobalDelegate) {
        if (enable) realtimeDatabaseServiceImpl.userDataListener(UserState) { exception ->
            snackbarDelegate.showSnackbar(SnackbarState.ERROR,
                exception?.localizedMessage.toString(), withDismissAction = true)
        } else {
            realtimeDatabaseServiceImpl.removeUserValueEventListener(userState)
        }
    }
    private fun clearEmailAndPasswordFields(){
        uiState.value = uiState.value.copy(emailLogin = "", passwordLogin = "")
    }

}
private fun checkCredentials(email: String?, password: String?) : Boolean {
    return email == "" || password == ""
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

    fun createAccount(email: String, password: String, snackbarDelegate: SnackbarGlobalDelegate) {
        viewModel.createAccount(email, password, snackbarDelegate)
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