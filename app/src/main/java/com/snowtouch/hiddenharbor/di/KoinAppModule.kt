package com.snowtouch.hiddenharbor.di

import androidx.compose.material3.SnackbarHostState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.snowtouch.hiddenharbor.data.repository.RealtimeDatabaseServiceImpl
import com.snowtouch.hiddenharbor.ui.components.SnackbarGlobalDelegate
import com.snowtouch.hiddenharbor.viewmodel.AccountScreenViewModel
import com.snowtouch.hiddenharbor.viewmodel.FavoritesScreenViewModel
import com.snowtouch.hiddenharbor.viewmodel.UserState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val firebaseModule = module {

    single {
        if (isFirebaseLocal) {
            FirebaseDatabase.getInstance("http://10.0.2.2:9299?ns=xxxx")
        } else {
            FirebaseDatabase.getInstance("https://xxxx-default-rtdb.europe-west1.firebasedatabase.app")
        }
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

    single { RealtimeDatabaseServiceImpl(get()) }

    single { UserState }
}
val viewModelModule = module {
    viewModel { AccountScreenViewModel(get(), get(), get()) }
    viewModel { FavoritesScreenViewModel(get()) }
}

val snackbarHostModule = module {
    single { SnackbarHostState() }
    single { CoroutineScope(SupervisorJob() + Dispatchers.Main) }
    single { SnackbarGlobalDelegate(get(), get()) }
}

var isFirebaseLocal = true