package com.snowtouch.hiddenharbor.data.repository

import com.snowtouch.hiddenharbor.data.model.Ad
import com.snowtouch.hiddenharbor.data.model.User

interface RealtimeDatabaseService {

    fun createUserData(user: User, onResult: (Throwable?) -> Unit)
    fun getUserData(user: User, onResult: (Throwable?) -> Unit)
    fun createAd(ad: Ad, onComplete: (Boolean) -> Unit, onFailure: (Throwable?) -> Unit)
}