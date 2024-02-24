package com.skybox.shopshowcase.presentation.ui.screens.product

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ProductScreen(productId: String, viewModel: ProductViewModel = hiltViewModel()) {
    LaunchedEffect(productId) {
        viewModel.loadProductInfo(productId)
    }
    Scaffold { paddingValues ->
        Column (Modifier.padding(paddingValues)){

        }
    }
}