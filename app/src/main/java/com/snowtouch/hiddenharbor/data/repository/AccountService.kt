package com.snowtouch.hiddenharbor.data.repository

interface AccountService {
    fun createAccount(email: String, password: String, onResult: (String?, Throwable?) -> Unit)
    fun authenticate(email: String, password: String, onResult: (Throwable?) -> Unit)
    fun deleteAccount(password: String, onResult: (Throwable?) -> Unit)
    fun signOut()
}