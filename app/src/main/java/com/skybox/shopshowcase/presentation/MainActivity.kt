package com.skybox.shopshowcase.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.skybox.shopshowcase.presentation.ui.screens.auth.AuthActivity
import com.skybox.shopshowcase.presentation.ui.screens.home.HomeScreen
import com.skybox.shopshowcase.presentation.ui.screens.home.HomeViewModel
import com.skybox.shopshowcase.presentation.ui.theme.ShopShowcaseTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<HomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().setKeepOnScreenCondition {
            if (viewModel.isLoggedIn()) {
                return@setKeepOnScreenCondition  false
            } else {
                startActivity(Intent(applicationContext, AuthActivity::class.java))
            }
            return@setKeepOnScreenCondition false
        }

        setContent {
            ShopShowcaseTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    HomeScreen(navController = navController)
                }
            }
        }
    }
}