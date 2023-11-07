package com.snowtouch.hiddenharbor.data.repository

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.snowtouch.hiddenharbor.data.model.Ad
import com.snowtouch.hiddenharbor.data.model.User
import com.snowtouch.hiddenharbor.viewmodel.UserState

class RealtimeDatabaseServiceImpl(firebaseDatabase: FirebaseDatabase): RealtimeDatabaseService {
    private var userValueEventListener: ValueEventListener? = null
    private val adsReference = firebaseDatabase.getReference("advertisements")
    private val userReference = firebaseDatabase.getReference("users")

    override fun userDataListener(userState: UserState, onResult: (Throwable?) -> Unit) {
        userValueEventListener = userReference
            .child(userState.user.value.uniqueId)
            .addValueEventListener(object : ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {
                    val updatedUser = snapshot.getValue(User::class.java)
                    userState.updateUserData(updatedUser)
                    Log.d("Database user listener", "Active")
                }

                override fun onCancelled(error: DatabaseError) {
                    onResult(Exception(error.message))
                }
            })
    }
    override fun removeUserValueEventListener(userState: UserState) {
        val listener = userValueEventListener
        if (listener != null) {
            userReference
                .child(userState.user.value.uniqueId)
                .removeEventListener(listener)
            Log.d("Database user listener", "Not active")
        }
        userValueEventListener = null
    }
    override suspend fun createUserData(userState: UserState) {
        userReference
            .child(userState.user.value.uniqueId)
            .setValue(userState.user.value)
    }
    override suspend fun createAd(ad: Ad, onComplete: (Boolean) -> Unit, onFailure: (Throwable?) -> Unit) {
        adsReference.push()
            .setValue(ad)
            .addOnCompleteListener { task ->
                if (task.isSuccessful && adsReference.key!=null) {
                    userReference
                        .child(UserState.user.value.uniqueId)
                        .child("ads").setValue(adsReference.key)
                    onComplete(true)
                } else { onComplete(false) }
            }
            .addOnFailureListener { onFailure(it.cause) }
    }

}