package com.snowtouch.hiddenharbor.viewmodel

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

abstract class AccountServiceImpl: AccountService {
    override fun createAccount(email: String, password: String, onResult: (Throwable?) -> Unit){
        Firebase.auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { onResult(it.exception) }
    }

    override fun authenticate(email: String, password: String, onResult: (Throwable?) -> Unit) {
        Firebase.auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { onResult(it.exception) }
    }

    override fun deleteAccount(email: String, password: String, onResult: (Throwable?) -> Unit) {
        Firebase.auth.currentUser?.delete()
    }
}