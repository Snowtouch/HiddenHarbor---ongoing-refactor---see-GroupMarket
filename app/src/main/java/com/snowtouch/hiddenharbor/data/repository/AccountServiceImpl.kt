package com.snowtouch.hiddenharbor.data.repository

import com.google.firebase.auth.FirebaseAuth

class AccountServiceImpl(private val firebaseAuth: FirebaseAuth): AccountService {
    override fun createAccount(email: String, password: String, onResult: (String?, Throwable?) -> Unit){
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onResult(firebaseAuth.currentUser?.uid, null)
                } else {
                    onResult(null, task.exception)
                }
            }
    }

    override fun authenticate(email: String, password: String, onResult: (Throwable?) -> Unit) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { onResult(it.exception) }
    }

    override fun deleteAccount(password: String, onResult: (Throwable?) -> Unit) {
        firebaseAuth.currentUser?.delete()
            ?.addOnCompleteListener { onResult(it.exception) }
    }

    override fun signOut() {
        firebaseAuth.signOut()
    }
}