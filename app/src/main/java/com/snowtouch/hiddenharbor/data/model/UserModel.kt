package com.snowtouch.hiddenharbor.data.model

data class User(
    val userLoggedIn: Boolean = false,
    val uniqueId: String = "",
    val email: String = "",
    val ads: List<Ad> = emptyList(),
    val favorites: List<Ad> = emptyList(),
    val conversations: List<Conversation> = emptyList()
)
