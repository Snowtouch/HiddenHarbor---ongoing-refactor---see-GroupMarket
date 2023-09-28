package com.snowtouch.hiddenharbor.data.model


data class Ad(
    val authorUid: String? = null,
    val title: String? = null,
    val description: String? = null,
    val price: Double? = null,
    val category: String? = null,
    val location: String? = null,
    val datePosted: String? = null,
    val photoUrls: List<String>? = null,
    val privacyLevel: AdPrivacyLevel
)
enum class AdPrivacyLevel{
    PUBLIC, GROUP
}