package com.snowtouch.hiddenharbor.data.repository

import android.net.Uri

interface StorageService {
    suspend fun uploadAdImageAndGetImageUrl(
        image: Uri,
        adDatabaseReferenceKey: String,
        onFailure: (Throwable?) -> Unit
    ) : Uri
}