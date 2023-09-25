package com.snowtouch.hiddenharbor.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.snowtouch.hiddenharbor.ui.AppUiState

class LoginScreenViewModel(
    private val accountServiceImpl: AccountServiceImpl
) : ViewModel() {
    var uiState = mutableStateOf(LoginUiState())
        private set
    var appState = mutableStateOf(AppUiState())
        private set

    fun onEmailChange(newValue: String) {
        uiState.value = uiState.value.copy(email = newValue)
    }
    fun onPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(password = newValue)
    }
    fun createAccount(email: String, password: String, context: Context) {
        accountServiceImpl.createAccount(email, password) { error ->
            if (error!=null)
            Toast.makeText(context, "$error", Toast.LENGTH_LONG)
                .show()
        }
    }
    fun signIn(email: String, password: String, context: Context) {
        accountServiceImpl.authenticate(email, password) { error ->
            if (error!=null)
            Toast.makeText(context, "$error", Toast.LENGTH_LONG)
                .show()
        }
    }
    fun signOut(){
        accountServiceImpl.signOut()
    }
}

data class LoginUiState(
    val email: String = "",
    val password: String = ""
)