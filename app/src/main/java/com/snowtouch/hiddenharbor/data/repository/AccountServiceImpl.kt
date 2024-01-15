package com.snowtouch.hiddenharbor.data.repository

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class AccountServiceImpl(
    private val firebaseAuth: FirebaseAuth,
    private val ioDispatcher: CoroutineDispatcher
): AccountService {
    override suspend fun createAccount(email: String, password: String, onResult: (String?) -> Unit) {
        withContext(ioDispatcher) {
            firebaseAuth.createUserWithEmailAndPassword(email, password).await()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        onResult(firebaseAuth.currentUser?.uid)
                    } else {
                        onResult(null)
                    }
                }
        }
    }

    override suspend fun authenticate(email: String, password: String) {
        withContext(ioDispatcher) {
            firebaseAuth.signInWithEmailAndPassword(email, password).await()
        }
    }

    override suspend fun deleteAccount(password: String) {
        withContext(ioDispatcher) {
            firebaseAuth.currentUser?.delete()?.await()
        }
    }

    override suspend fun signOut() {
        withContext(ioDispatcher) { firebaseAuth.signOut() }
    }
}