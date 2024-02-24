package com.skybox.shopshowcase.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.skybox.shopshowcase.presentation.ui.screens.cart.CartScreen
import com.skybox.shopshowcase.presentation.ui.screens.home.HomeScreen
import com.skybox.shopshowcase.presentation.ui.theme.ShopShowcaseTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShopShowcaseTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    NavHost(navController = navController, startDestination = "/") {
                        composable("/") {
                            HomeScreen()
                        }
                        composable("/cart") {
                            CartScreen()
                        }
                    }
                }
            }
        }
    }
}