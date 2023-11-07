package com.snowtouch.hiddenharbor.data.repository

interface AccountService {
    suspend fun createAccount(email: String, password: String, onResult: (String?) -> Unit)
    suspend fun authenticate(email: String, password: String)
    suspend fun deleteAccount(password: String)
    suspend fun signOut()
}