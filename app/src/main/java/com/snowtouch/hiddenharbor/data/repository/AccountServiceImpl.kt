package com.snowtouch.hiddenharbor.data.repository

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class AccountServiceImpl(
    private val firebaseAuth: FirebaseAuth,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
): AccountService {
    override suspend fun createAccount(email: String, password: String, uid: (String?) -> Unit) {
        withContext(ioDispatcher) {
            firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            uid(firebaseAuth.currentUser?.uid)
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