package com.snowtouch.hiddenharbor.data.model

import java.text.DecimalFormat


data class Ad(
    val adId: String = "",
    val userId: String? = null,
    val title: String = "",
    val description: String? = null,
    val price: Double? = null,
    val category: String? = null,
    val group: String? = null,
    val location: String? = null,
    val datePosted: String = "",
    val photoUrls: List<String>? = null,
    val isFavorite: Boolean = false,
    val privacyLevel: AdPrivacyLevel? = null,
    val adStatus: AdStatus? = null
) {
    fun getFormattedPrice(): String {
        val decimalFormat = DecimalFormat("#.##")
        return decimalFormat.format(price)
    }
}
enum class AdPrivacyLevel{
    PUBLIC, GROUP
}
enum class AdStatus{
    FINISHED, ACTIVE, DRAFT
}