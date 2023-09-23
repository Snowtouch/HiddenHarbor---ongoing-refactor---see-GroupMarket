package com.snowtouch.hiddenharbor.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel

class LoginScreenViewModel(
    private val accountServiceImpl: AccountServiceImpl
) : ViewModel() {
    var uiState = mutableStateOf(LoginUiState())
        private set

    fun onEmailChange(newValue: String) {
        uiState.value = uiState.value.copy(email = newValue)
    }
    fun onPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(password = newValue)
    }
    fun createAccount(email: String, password: String, context: Context) {
        accountServiceImpl.createAccount(email, password) { error ->
            Toast.makeText(context, "$error", Toast.LENGTH_LONG)
                .show()
        }
    }
}

data class LoginUiState(
    val email: String = "",
    val password: String = ""
)