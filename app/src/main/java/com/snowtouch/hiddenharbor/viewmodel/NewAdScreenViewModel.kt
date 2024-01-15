package com.snowtouch.hiddenharbor.viewmodel

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.snowtouch.hiddenharbor.data.model.Ad
import com.snowtouch.hiddenharbor.data.model.User
import com.snowtouch.hiddenharbor.data.repository.RealtimeDatabaseServiceImpl
import com.snowtouch.hiddenharbor.data.repository.StorageServiceImpl
import kotlinx.coroutines.flow.StateFlow

class NewAdScreenViewModel(
    userState: UserState,
    private val realtimeDatabaseServiceImpl: RealtimeDatabaseServiceImpl,
    private val storageServiceImpl: StorageServiceImpl
    ) : ViewModel() {

    val user: StateFlow<User> = userState.user
    val userLoggedIn: StateFlow<Boolean> = userState.userLoggedIn
    var adUiState by mutableStateOf(AdUiState())

    fun updateAdUiState(updatedAd: Ad) {
        adUiState = adUiState.copy(ad = updatedAd)
    }
    fun getValidatedPrice(price: String): String {
        val filteredChars = price.filterIndexed { index, c ->
            c.isDigit()
                    || (c == '.' && index != 0 && price.indexOf('.') == index)
                    || (c == '.' && index != 0 && price.count { it == '.' } <= 1)
        }
        val trimmedPrice = filteredChars.removePrefix("0")
        if (trimmedPrice.isBlank()) {
            return ""
        }
        return if (trimmedPrice.count { it == '.' } == 1) {
            val beforeDecimal = trimmedPrice.substringBefore('.')
            val afterDecimal = trimmedPrice.substringAfter('.')
            beforeDecimal + "." + afterDecimal.take(2)
        } else {
            trimmedPrice
        }
    }
    fun postAdvertisement() {

    }
    private fun uploadImages(images: List<Uri>) : List<String>{
        images.forEach { image ->
            try {

            }
            catch {

            }
        }
    }
}
data class AdUiState( var ad: Ad = Ad() )