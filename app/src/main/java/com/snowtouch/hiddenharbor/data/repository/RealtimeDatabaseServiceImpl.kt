package com.snowtouch.hiddenharbor.data.repository

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.snowtouch.hiddenharbor.data.model.Ad
import com.snowtouch.hiddenharbor.data.model.User
import com.snowtouch.hiddenharbor.viewmodel.UserState

class RealtimeDatabaseServiceImpl(private val firebaseDatabase: FirebaseDatabase): RealtimeDatabaseService {
    private var userValueEventListener: ValueEventListener? = null
    override fun userDataListener(userState: UserState, onResult: (Throwable?) -> Unit) {
        userValueEventListener = firebaseDatabase.getReference("users")
            .child(userState.user.value.uniqueId)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val updatedUser = snapshot.getValue(User::class.java)
                    userState.updateUserData(updatedUser)
                    onResult(null)
                }

                override fun onCancelled(error: DatabaseError) {
                    onResult(Exception(error.message))
                }
            })
    }
    override fun removeUserValueEventListener(userState: UserState) {
        val listener = userValueEventListener
        if (listener != null) {
            firebaseDatabase.getReference("users")
                .child(userState.user.value.uniqueId)
                .removeEventListener(listener)
        }
        userValueEventListener = null
    }
    override fun createUserData(uid: String, email: String, onResult: (Throwable?) -> Unit) {
        val user = User(email = email)
        firebaseDatabase.getReference("users")
            .child(uid)
            .setValue(user)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onResult(null)
                } else {
                    onResult(task.exception)
                }
            }
    }
    override fun createAd(ad: Ad, onComplete: (Boolean) -> Unit, onFailure: (Throwable?) -> Unit) {
        firebaseDatabase.getReference("advertisements").push()
            .setValue(ad)
            .addOnCompleteListener { onComplete(it.isComplete) }
            .addOnFailureListener { onFailure(it.cause) }
    }
}