package com.snowtouch.hiddenharbor.di

import android.app.Application
import androidx.compose.material3.SnackbarHostState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.snowtouch.hiddenharbor.R
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

//VARIABLE TO KEEP PERSONAL DATA HIDDEN



val firebaseModule = module {

    single {
        val firebaseDatabaseLocalAddress = get<Application>().getString(R.string.firebaseDatabaseLocal)
        val firebaseDatabaseRemoteAddress = get<Application>().getString(R.string.firebaseDatabaseRemote)
        if (isFirebaseLocal) {
            FirebaseDatabase.getInstance(firebaseDatabaseLocalAddress)
        } else {
            FirebaseDatabase.getInstance(firebaseDatabaseRemoteAddress)
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
        val firebaseStorageLocalAddress = get<Application>().getString(R.string.firebaseStorageLocal)
        if (isFirebaseLocal) {
            FirebaseStorage.getInstance(firebaseStorageLocalAddress)
        } else {
            FirebaseStorage.getInstance()
        }
    }

    single { RealtimeDatabaseServiceImpl(get()) }


}
@OptIn(ExperimentalStdlibApi::class)
val viewModelModule = module {
    single { UserState }
    viewModel { AccountScreenViewModel(get(), get(), get()) }
    viewModel { FavoritesScreenViewModel(get()) }
}

val snackbarHostModule = module {
    single { SnackbarHostState() }
    single { CoroutineScope(SupervisorJob() + Dispatchers.Main) }
    single { SnackbarGlobalDelegate(get(), get()) }
}

var isFirebaseLocal = true