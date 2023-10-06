package com.snowtouch.hiddenharbor

import android.app.Application
import com.snowtouch.hiddenharbor.di.firebaseModule
import com.snowtouch.hiddenharbor.di.isFirebaseLocal
import com.snowtouch.hiddenharbor.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        isFirebaseLocal = true
        if (isFirebaseLocal) {
            startKoin {
                androidContext(this@MainApplication)
                modules(firebaseModule, viewModelModule)
            }
        }
    }
}
