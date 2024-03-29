package com.skybox.shopshowcase.presentation.ui.screens.cart

import android.graphics.drawable.Drawable
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.integration.compose.rememberGlidePreloadingData
import com.skybox.shopshowcase.domain.model.Cart
import com.skybox.shopshowcase.domain.model.CartItem
import com.skybox.shopshowcase.presentation.ui.components.EmptyState
import com.skybox.shopshowcase.presentation.ui.components.ProductImage
import com.skybox.shopshowcase.presentation.ui.components.QuantityRow
import com.skybox.shopshowcase.presentation.ui.components.RecommendedProducts
import com.skybox.shopshowcase.util.formatCurrency
import com.skybox.shopshowcase.util.toProductRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(navigate: (route: String) -> Unit, viewModel: CartViewModel = hiltViewModel()) {
    val cart by viewModel.cartFlow.collectAsState(Cart())
    val relatedState by viewModel.relatedProducts.collectAsState()
    val isCartEmpty = cart.cartItems.isEmpty()
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(state) {
        if (state is CartScreenState.Error) {
            Toast.makeText(context,context.getString((state as CartScreenState.Error).stringRes), Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(topBar = {
        MediumTopAppBar(title = { Text(text = "Cart") }, actions = {
            if (isCartEmpty.not()) {
                IconButton(onClick = viewModel::clearCart) {
                    Icon(Icons.Filled.DeleteForever, contentDescription = "")
                }
            }
        })
    }, bottomBar = {
        if (isCartEmpty.not()) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Column {
                        Text(text = "Total")
                        Text(text = cart.total.formatCurrency())
                    }
                    OutlinedButton(onClick = { viewModel.placeOrder(cart) }) {
                        Text(text = "Checkout")
                        Icon(Icons.Filled.ChevronRight, contentDescription = "")
                    }
                }
            }
        }
    }) { paddingValues ->
        if (isCartEmpty) {
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp)
            ) {
                EmptyState()
            }
        } else {
            val glidePreloadingData =
                rememberGlidePreloadingData(
                    data = cart.cartItems,
                    preloadImageSize = Size(40f, 40f),
                    numberOfItemsToPreload = 15,
                    fixedVisibleItemCount = 2,
                ) { item, requestBuilder ->
                    requestBuilder.load(item.image).signature(item.signature())
                }

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp)
            ) {
                items(glidePreloadingData.size) { index ->
                    val (item, preloadRequest) = glidePreloadingData[index]
                    CartItemCard(
                        item = item,
                        preloadRequest = preloadRequest,
                        delete = { viewModel.removeFromCart(item.productId) },
                        increment = { viewModel.incrementCartItem(item) },
                        decrement = { viewModel.decrementCartItem(item) })
                }
                item {
                    RecommendedProducts(onClick = {
                        navigate(it.toProductRoute())
                    },relatedState = relatedState)
                }
            }
        }

    }
}

@Composable
fun CartItemCard(
    item: CartItem,
    preloadRequest: RequestBuilder<Drawable>,
    delete: () -> Unit,
    increment: () -> Unit,
    decrement: () -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    ProductImage(
                        url = item.image,
                        signature = item.signature(),
                        preloadRequest = preloadRequest,
                        modifier = Modifier
                            .height(40.dp)
                            .width(40.dp)
                            .clip(RoundedCornerShape(4.dp))
                    )
                    Text(text = item.productName)
                }

                IconButton(onClick = delete) {
                    Icon(Icons.Filled.Delete, contentDescription = "")
                }
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = item.price.formatCurrency())
                QuantityRow(
                    quantity = item.quantity,
                    onIncrement = increment,
                    onDecrement = decrement
                )
            }
        }
    }
}