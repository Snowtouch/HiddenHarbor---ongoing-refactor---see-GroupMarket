package com.snowtouch.hiddenharbor.data.model

data class Conversation(
    val conversationId: String,
    val usersId: List<User>,
)
