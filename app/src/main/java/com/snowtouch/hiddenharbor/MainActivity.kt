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
import com.snowtouch.hiddenharbor.data.repository.AccountServiceImpl
import com.snowtouch.hiddenharbor.ui.components.NavigationComponent
import com.snowtouch.hiddenharbor.ui.theme.HiddenHarborTheme
import com.snowtouch.hiddenharbor.viewmodel.AccountScreenViewModel
import com.snowtouch.hiddenharbor.viewmodel.UserState
import org.koin.android.ext.android.get

class MainActivity : ComponentActivity() {
    //private  val firebaseAuth: FirebaseAuth by inject()
    private lateinit var accountScreenViewModel: AccountScreenViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        accountScreenViewModel = AccountScreenViewModel(AccountServiceImpl(get()))

        super.onCreate(savedInstanceState)
        setContent {
            HiddenHarborTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavigationComponent(navController = navController, accountScreenViewModel)
                }
            }
        }
    }

    public override fun onStart() {
        super.onStart()
        val firebaseAuth: FirebaseAuth = get()
        if (firebaseAuth.currentUser != null) {
            UserState.setUserLoggedIn(true)
        }
    }
}