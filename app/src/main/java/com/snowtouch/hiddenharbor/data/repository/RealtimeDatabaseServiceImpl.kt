package com.snowtouch.hiddenharbor.data.repository

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.snowtouch.hiddenharbor.data.model.Ad
import com.snowtouch.hiddenharbor.data.model.User

class RealtimeDatabaseServiceImpl(private val firebaseDatabase: FirebaseDatabase): RealtimeDatabaseService {

    override fun createUserData(user: User, onResult: (Throwable?) -> Unit) {
        firebaseDatabase.getReference("users")
            .setValue(user)
            .addOnCompleteListener { onResult(it.exception) }
    }
    override fun getUserData(user: User, onResult: (Throwable?) -> Unit) {
        firebaseDatabase.getReference("users")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    TODO("Not yet implemented")
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
    }
    override fun createAd(ad: Ad, onComplete: (Boolean) -> Unit, onFailure: (Throwable?) -> Unit) {
        firebaseDatabase.getReference("advertisements")
            .setValue(ad)
            .addOnCompleteListener { onComplete(it.isComplete) }
            .addOnFailureListener { onFailure(it.cause) }
    }
}