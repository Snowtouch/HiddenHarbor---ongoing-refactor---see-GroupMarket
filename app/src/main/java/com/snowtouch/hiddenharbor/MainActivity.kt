package com.snowtouch.hiddenharbor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.snowtouch.hiddenharbor.di.firebaseModule
import com.snowtouch.hiddenharbor.di.isFirebaseLocal
import com.snowtouch.hiddenharbor.repository.AccountServiceImpl
import com.snowtouch.hiddenharbor.ui.AppUiState
import com.snowtouch.hiddenharbor.ui.NavigationComponent
import com.snowtouch.hiddenharbor.ui.theme.HiddenHarborTheme
import com.snowtouch.hiddenharbor.viewmodel.LoginScreenViewModel
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainActivity : ComponentActivity() {
    private  val firebaseAuth: FirebaseAuth by inject()
    private lateinit var loginScreenViewModel: LoginScreenViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        isFirebaseLocal = true

        startKoin {
            androidContext(this@MainActivity)
            modules(firebaseModule)
        }

        loginScreenViewModel = LoginScreenViewModel(AccountServiceImpl(firebaseAuth))

        super.onCreate(savedInstanceState)
        setContent {
            HiddenHarborTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavigationComponent(navController = navController, loginScreenViewModel)
                }
            }
        }
    }
    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        if (firebaseAuth.currentUser != null) {
            AppUiState(true)
        }
    }
}