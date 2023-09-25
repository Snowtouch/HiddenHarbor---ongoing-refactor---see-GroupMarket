package com.snowtouch.hiddenharbor.viewmodel

interface AccountService {
    fun createAccount(email: String, password: String, onResult: (Throwable?) -> Unit)
    fun authenticate(email: String, password: String, onResult: (Throwable?) -> Unit)
    fun deleteAccount(password: String, onResult: (Throwable?) -> Unit)
    fun signOut()
}