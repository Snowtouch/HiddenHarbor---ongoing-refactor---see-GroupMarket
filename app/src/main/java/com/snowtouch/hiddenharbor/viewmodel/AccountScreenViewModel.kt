package com.snowtouch.hiddenharbor.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.snowtouch.hiddenharbor.data.model.User
import com.snowtouch.hiddenharbor.data.repository.AccountServiceImpl
import com.snowtouch.hiddenharbor.data.repository.RealtimeDatabaseService
import kotlinx.coroutines.flow.StateFlow

class AccountScreenViewModel(
    private val userState: UserState,
    private val accountServiceImpl: AccountServiceImpl,
    private val realtimeDatabaseService: RealtimeDatabaseService
) : ViewModel() {
    var uiState = mutableStateOf(LoginUiState())
        private set
    val user: StateFlow<User> = userState.user

    private fun clearEmailAndPasswordFields(){
        uiState.value = uiState.value.copy(email = "", password = "")
    }
    private fun checkCredentials(email: String?, password: String?) : Boolean {
        return email == "" || password == ""
    }
    fun onEmailChange(newValue: String) {
        uiState.value = uiState.value.copy(email = newValue)
    }
    fun onPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(password = newValue)
    }
    fun createAccount(email: String, password: String, context: Context) {
        if (checkCredentials(email, password)) {
            Toast.makeText(context, "Enter valid credentials", Toast.LENGTH_LONG)
                .show()
        } else {
            accountServiceImpl.createAccount(email, password) { error ->
                if (error!=null) {
                    Toast.makeText(context, error.localizedMessage, Toast.LENGTH_LONG)
                        .show()
                }
            }
            realtimeDatabaseService.createUserData(userState.user.value) { error ->
                if (error!=null) {
                    Toast.makeText(context, error.localizedMessage, Toast.LENGTH_LONG)
                        .show()
                }
            }
        }

    }
    fun signIn(email: String, password: String, context: Context) {
        if (checkCredentials(email, password)) {
            Toast.makeText(context, "Enter valid credentials", Toast.LENGTH_LONG)
                .show()
        } else {
            accountServiceImpl.authenticate(email, password) { error ->
                if (error!=null) {
                    Toast.makeText(context, error.localizedMessage, Toast.LENGTH_LONG)
                       .show()
                } else {
                    userState.setUserLoggedIn(true)
                }
            }
        }
    }
    fun signOut(){
        accountServiceImpl.signOut()
        userState.setUserLoggedIn(false)
        clearEmailAndPasswordFields()
    }
}

data class LoginUiState(
    val email: String = "",
    val password: String = ""
)