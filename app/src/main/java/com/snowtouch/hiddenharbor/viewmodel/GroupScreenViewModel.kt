package com.snowtouch.hiddenharbor.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class GroupScreenViewModel() : ViewModel() {
    var appUiState = mutableStateOf(AppUiState())
}