package com.snowtouch.hiddenharbor.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.snowtouch.hiddenharbor.data.repository.AccountServiceImpl

class AccountScreenViewModel(
    private val accountServiceImpl: AccountServiceImpl
) : ViewModel() {
    var uiState = mutableStateOf(LoginUiState())
        private set
    val userLoggedInState = UserState.userLoggedIn

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
                    UserState.setUserLoggedIn(true)
                }
            }
        }
    }
    fun signOut(){
        accountServiceImpl.signOut()
        UserState.setUserLoggedIn(false)
        clearEmailAndPasswordFields()
    }
}

data class LoginUiState(
    val email: String = "",
    val password: String = ""
)