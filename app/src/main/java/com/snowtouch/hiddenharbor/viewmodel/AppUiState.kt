package com.snowtouch.hiddenharbor.viewmodel

import com.snowtouch.hiddenharbor.data.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


object UserState {
    private val _user = MutableStateFlow(User())
    val user: StateFlow<User> = _user.asStateFlow()

    fun setUserLoggedIn(loggedIn: Boolean) {
        _user.value = _user.value.copy(userLoggedIn = loggedIn)
    }
}