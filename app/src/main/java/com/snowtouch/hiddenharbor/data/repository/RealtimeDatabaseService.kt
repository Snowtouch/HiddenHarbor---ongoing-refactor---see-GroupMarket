package com.snowtouch.hiddenharbor.data.repository

import com.snowtouch.hiddenharbor.data.model.Ad
import com.snowtouch.hiddenharbor.viewmodel.UserState

interface RealtimeDatabaseService {

    fun userDataListener(userState: UserState, onResult: (Throwable?) -> Unit)
    fun removeUserValueEventListener(userState: UserState)
    suspend fun createUserData(userState: UserState)
    suspend fun createAd(ad: Ad, onComplete: (Boolean) -> Unit, onFailure: (Throwable?) -> Unit)
}