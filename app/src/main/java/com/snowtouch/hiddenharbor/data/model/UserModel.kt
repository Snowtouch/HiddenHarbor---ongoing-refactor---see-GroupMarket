package com.snowtouch.hiddenharbor.data.model

data class User(
    val uniqueId: String = "",
    val email: String = "",
    val ads: List<String?> = emptyList(),
    val favorites: List<String?> = emptyList(),
    val groups: List<String?> = emptyList(),
    val conversations: List<Conversation> = emptyList()
)
