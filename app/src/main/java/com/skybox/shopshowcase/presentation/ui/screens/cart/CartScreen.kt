package com.skybox.shopshowcase.presentation.ui.screens.cart

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.skybox.shopshowcase.domain.Cart

@Composable
fun CartScreen(viewModel: CartViewModel = hiltViewModel()) {
    val state by viewModel.cartState.collectAsState(Cart())

    Scaffold {paddingValues ->
        LazyColumn(Modifier.padding(paddingValues)) {
            items(state.cartItems) {item ->
                Card {
                    Text(text = item.product.name)
                }
            }
        }
    }
}