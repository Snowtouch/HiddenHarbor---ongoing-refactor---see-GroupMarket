package com.snowtouch.hiddenharbor.viewmodel

import com.snowtouch.hiddenharbor.data.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


object UserState {
    private val _user = MutableStateFlow(User())
    val user: StateFlow<User> = _user.asStateFlow()

    private val _userLoggedIn = MutableStateFlow(false)
    val userLoggedIn: StateFlow<Boolean> = _userLoggedIn.asStateFlow()

    fun setUserLoggedIn(loggedIn: Boolean) {
        _userLoggedIn.value = loggedIn
        if (!loggedIn) _user.value = User()
    }

    fun updateUserData(user: User?) {
        _user.value = user ?: User()
    }
}