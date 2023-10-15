package com.snowtouch.hiddenharbor.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.snowtouch.hiddenharbor.data.model.Ad
import com.snowtouch.hiddenharbor.data.model.User
import kotlinx.coroutines.flow.StateFlow

class NewAdScreenViewModel(private val userState: UserState) : ViewModel() {
    val user: StateFlow<User> = userState.user
    val userLoggedIn: StateFlow<Boolean> = userState.userLoggedIn

    var adUiState by mutableStateOf(AdUiState())



    fun updateAdUiState(updatedAd: Ad)
    {
        adUiState = adUiState.copy(ad = updatedAd)
    }
}
data class AdUiState( var ad: Ad = Ad() )