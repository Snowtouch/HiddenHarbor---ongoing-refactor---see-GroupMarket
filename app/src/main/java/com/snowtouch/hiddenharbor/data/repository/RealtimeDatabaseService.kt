package com.snowtouch.hiddenharbor.data.repository

import com.snowtouch.hiddenharbor.data.model.Ad
import com.snowtouch.hiddenharbor.viewmodel.UserState

interface RealtimeDatabaseService {

    fun userDataListener(userState: UserState, onResult: (Throwable?) -> Unit)
    fun removeUserValueEventListener(userState: UserState)
    fun createUserData(uid: String, email: String, onResult: (Throwable?) -> Unit)
    fun createAd(ad: Ad, onComplete: (Boolean) -> Unit, onFailure: (Throwable?) -> Unit)
}