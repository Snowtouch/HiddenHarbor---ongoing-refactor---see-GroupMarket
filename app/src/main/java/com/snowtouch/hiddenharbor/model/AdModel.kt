package com.snowtouch.hiddenharbor.model


data class AdModel(
    val id: Int,
    val ownerUid: Long,
    val privacyLevel: AdPrivacyLevel,
    val images: List<Int>,
    val description: String,
    val price: Int,
    val dateAdded: String
)
enum class AdPrivacyLevel{
    PUBLIC, GROUP
}