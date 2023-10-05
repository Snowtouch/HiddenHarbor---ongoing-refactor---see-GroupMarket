package com.snowtouch.hiddenharbor.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.snowtouch.hiddenharbor.data.model.User
import com.snowtouch.hiddenharbor.data.repository.AccountServiceImpl
import com.snowtouch.hiddenharbor.data.repository.RealtimeDatabaseServiceImpl
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
            accountServiceImpl.createAccount(email, password) { uid, error ->
                if (error!=null) {
                    toastMessage(context, error.localizedMessage)
                } else if(uid!=null) {
                    realtimeDatabaseServiceImpl.createUserData(uid, email) { exception ->
                        toastMessage(context, exception?.localizedMessage)
                    }
                }
            }
        }
    }
    fun login(email: String, password: String, context: Context) {
        if (checkCredentials(email, password)) {
            Toast.makeText(context, "Enter valid credentials", Toast.LENGTH_LONG)
                .show()
        } else {
            accountServiceImpl.authenticate(email, password) { error ->
                if (error!=null) {
                    toastMessage(context, error.localizedMessage)
                } else {
                    userState.setUserLoggedIn(true)
                    realtimeDatabaseServiceImpl.userDataListener(UserState) { exception ->
                        toastMessage(context, exception.toString())
                    }
                }
            }
        }
    }
    fun signOut(){
        accountServiceImpl.signOut()
        userState.setUserLoggedIn(false)
        realtimeDatabaseServiceImpl.removeUserValueEventListener(userState)
        clearEmailAndPasswordFields()
    }
}
private fun toastMessage(context: Context, message: String?) {
    if (message?.isNotEmpty() == true) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}
data class LoginUiState(
    val email: String = "",
    val password: String = ""
)