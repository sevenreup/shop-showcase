package com.skybox.shopshowcase.presentation.ui.screens.cart

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CartScreen() {
    Scaffold {paddingValues ->
        Column(Modifier.padding(paddingValues)) {
            Text(text = "Cart")
        }
    }
}