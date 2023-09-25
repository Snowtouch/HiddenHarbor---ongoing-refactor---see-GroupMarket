package com.snowtouch.hiddenharbor.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.snowtouch.hiddenharbor.viewmodel.LoginScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val firebaseModule = module {

    single {
        if (isFirebaseLocal) {
            FirebaseDatabase.getInstance("http://10.0.2.2:9000?ns=xxxx")
        } else {
            FirebaseDatabase.getInstance()
        }
        //val database = Firebase.database
        //database.useEmulator("10.0.2.2", 9000)
    }
    single {
        val auth = FirebaseAuth.getInstance()
        if (isFirebaseLocal) {
            auth.useEmulator("10.0.2.2", 9099)
        }
        auth
    }
    single {
        if (isFirebaseLocal) {
            FirebaseStorage.getInstance("http://10.0.2.2:9199?ns=xxxx")
        } else {
            FirebaseStorage.getInstance()
        }
    }
    viewModel { LoginScreenViewModel(get()) }
}
var isFirebaseLocal = false