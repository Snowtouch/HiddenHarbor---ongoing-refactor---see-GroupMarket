package com.snowtouch.hiddenharbor.data.repository

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage


class StorageServiceImpl(firebaseStorage: FirebaseStorage) : StorageService {
    private val storageRef = firebaseStorage.reference
    override suspend fun uploadAdImageAndGetImageUrl(
        image: Uri,
        adDatabaseReferenceKey: String,
        onFailure: (Throwable?) -> Unit
    ) : Uri {
        val imagesRef = storageRef.child("images/${adDatabaseReferenceKey}/${image.lastPathSegment}")
        val uploadTask = imagesRef.putFile(image)

        uploadTask.continueWithTask { task ->
            if(!task.isSuccessful) {
                onFailure(task.exception?.cause)
            }
            imagesRef.downloadUrl
        }
            .addOnCompleteListener { task ->
                if (task.isSuccessful) { val downloadUrl = task.result }
                else onFailure(task.exception?.cause)

            }
        return downloadUrl
    }
}