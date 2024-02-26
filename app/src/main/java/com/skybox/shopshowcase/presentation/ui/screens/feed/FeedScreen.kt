package com.skybox.shopshowcase.presentation.ui.screens.feed

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.skybox.shopshowcase.data.entities.ProductEntity
import com.skybox.shopshowcase.util.toProductRoute

@Composable
fun FeedScreen(navigate: (route: String) -> Unit, viewModel: FeedScreenViewModel = hiltViewModel()) {
    val uiState by viewModel.state.collectAsState()

    Scaffold { paddingValues ->
        if (uiState.loading) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(paddingValues)
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(18.dp),
                modifier = Modifier.padding(paddingValues)
            ) {
                items(uiState.products.size) { idx ->
                    val product = uiState.products[idx]
                    ProductItem(product = product, onClick = {
                        navigate(product.productId.toProductRoute())
                    }, onProductAdd = {
                        viewModel.addToCart(product)
                    })
                }
            }
        }

    }
}

@Composable
fun ProductItem(product: ProductEntity, onClick: () -> Unit, onProductAdd: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(text = product.name)
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "MK ${product.price}")
                FilledIconButton(onClick = onProductAdd) {
                    Icon(Icons.Filled.Add, contentDescription = "Add Icon")
                }
            }
        }

    }
}