package com.skybox.shopshowcase.presentation.ui.screens.feed

import android.graphics.drawable.Drawable
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.rememberGlidePreloadingData
import com.bumptech.glide.signature.ObjectKey
import com.skybox.shopshowcase.domain.Product
import com.skybox.shopshowcase.presentation.ui.components.ProductImage
import com.skybox.shopshowcase.util.formatCurrency
import com.skybox.shopshowcase.util.toProductRoute

@Composable
fun FeedScreen(
    navigate: (route: String) -> Unit,
    viewModel: FeedScreenViewModel = hiltViewModel()
) {
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
            val glidePreloadingData =
                rememberGlidePreloadingData(
                    data = uiState.products,
                    preloadImageSize = Size(150f, 150f),
                    numberOfItemsToPreload = 15,
                    fixedVisibleItemCount = 2,
                ) { item, requestBuilder ->
                    requestBuilder.load(item.thumbnail).signature(item.signature())
                }
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(18.dp),
                modifier = Modifier.padding(paddingValues)
            ) {
                items(
                    glidePreloadingData.size,
                    key = { idx -> uiState.products[idx].productId }) { idx ->
                    val (product, preloadRequest) = glidePreloadingData[idx]
                    ProductItem(product = product, preloadRequest = preloadRequest, onClick = {
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
fun ProductItem(
    product: Product,
    preloadRequest: RequestBuilder<Drawable>,
    onClick: () -> Unit,
    onProductAdd: () -> Unit
) {
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
            ProductImage(
                url = product.thumbnail,
                signature = product.signature(),
                preloadRequest = preloadRequest,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp).clip(RoundedCornerShape(6.dp))
            )
            Text(text = product.name)
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = product.price.formatCurrency())
                FilledIconButton(onClick = onProductAdd) {
                    Icon(Icons.Filled.Add, contentDescription = "Add Icon")
                }
            }
        }

    }
}