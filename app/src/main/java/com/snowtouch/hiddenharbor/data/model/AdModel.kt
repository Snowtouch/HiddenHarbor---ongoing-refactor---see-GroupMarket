package com.snowtouch.hiddenharbor.data.model

import android.net.Uri


data class Ad(
    val userId: String? = null,
    val title: String = "",
    val description: String = "",
    val price: String = "",
    val category: Int? = null,
    val subcategory: Int? = null,
    val groupId: String? = null,
    val location: String? = null,
    val datePosted: String = "",
    val photoUrls: List<Uri>? = null,
    val isFavorite: Boolean = false,
    val privacyLevel: AdPrivacyLevel = AdPrivacyLevel.PUBLIC,
    val adStatus: AdStatus = AdStatus.DRAFT
)
enum class AdPrivacyLevel{
    PUBLIC, GROUP
}
enum class AdStatus{
    FINISHED, ACTIVE, DRAFT
}