package com.skybox.shopshowcase.presentation.ui.screens.product

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.skybox.shopshowcase.R
import com.skybox.shopshowcase.domain.LoadableState
import com.skybox.shopshowcase.domain.Product
import com.skybox.shopshowcase.domain.ProductDetails
import com.skybox.shopshowcase.presentation.ui.components.ProductImage
import com.skybox.shopshowcase.util.formatCurrency

@Composable
fun ProductScreen(
    productId: String,
    navigateBack: () -> Unit,
    viewModel: ProductViewModel = hiltViewModel()
) {
    LaunchedEffect(productId) {
        viewModel.loadProductInfo(productId)
    }
    val productState by viewModel.productDetails.collectAsState()
    val relatedState by viewModel.relatedProducts.collectAsState()

    when (productState) {
        is LoadableState.Loading -> {
            return CircularProgressIndicator()
        }

        is LoadableState.Success -> {
            ProductScreenContainer(
                productDetails = (productState as LoadableState.Success<ProductDetails>).data,
                navigateBack = navigateBack,
                addToCart = viewModel::addToCart,
                relatedState = relatedState
            )
        }

        is LoadableState.Error -> {
            return CircularProgressIndicator()
        }

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductScreenContainer(
    productDetails: ProductDetails,
    addToCart: () -> Unit,
    navigateBack: () -> Unit,
    relatedState: LoadableState<List<Product>>
) {
    val product = productDetails.product

    Scaffold(topBar = {
        TopAppBar(title = { Text(text = stringResource(id = R.string.details)) }, navigationIcon = {
            IconButton(onClick = navigateBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "")
            }
        })
    }, bottomBar = {
        ProductBottomAppBar(product.price, addToCart)
    }) { paddingValues ->
        LazyColumn(
            Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            item {
                Text(text = product.name, style = MaterialTheme.typography.headlineMedium)
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    product.categories.firstOrNull()?.let { Text(text = it) }
                    VerticalDivider(Modifier.height(12.dp))
                    Text(text = product.brand)
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(Icons.Filled.Star, contentDescription = "rating")
                        Text(text = product.rating.toString())
                    }

                    VerticalDivider(Modifier.height(12.dp))
                    Text(
                        text = product.brand
                    )
                }
                Spacer(modifier = Modifier.height(14.dp))
                ProductImagePager(images = product.images)
                Spacer(modifier = Modifier.height(12.dp))
                Text(text = product.description, style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(12.dp))
                RecommendedProducts(relatedState = relatedState)
            }
        }
    }
}

@Composable
fun RecommendedProducts(relatedState: LoadableState<List<Product>>) {
    if (relatedState is LoadableState.Success) {
        Text(text = "Recommended")
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow(
            contentPadding = PaddingValues(8.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            val items = relatedState.data
            items(items) { product ->
                Card {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp),modifier = Modifier.padding(12.dp)) {
                        ProductImage(
                            url = product.thumbnail, modifier = Modifier
                                .width(200.dp)
                                .height(
                                    120.dp
                                )
                                .clip(RoundedCornerShape(12.dp))
                        )
                        Text(text = product.name)
                        Text(text = product.price.formatCurrency())
                    }

                }
            }
        }
    }
}

@Composable
fun ProductBottomAppBar(price: Double, addToCart: () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(14.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(bottom = 12.dp)
    ) {
        Text(
            text = price.formatCurrency(),
            style = MaterialTheme.typography.headlineSmall
        )
        Button(modifier = Modifier.fillMaxWidth(), onClick = addToCart) {
            Text(text = stringResource(id = R.string.add_to_cart))
        }
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalGlideComposeApi::class)
@Composable
fun ProductImagePager(images: List<String>) {
    val imageModifier = Modifier
        .fillMaxWidth()
        .height(240.dp)
        .clip(RoundedCornerShape(8.dp))
    if (images.size == 1) {
        ProductImage(
            url = images.first(),
            contentDescription = "Image",
            modifier = imageModifier,
        ) {
            it.centerCrop()
        }
    } else {
        val pagerState = rememberPagerState(pageCount = { images.size })
        Column {
            HorizontalPager(
                state = pagerState,
                contentPadding = PaddingValues(horizontal = 32.dp),
                pageSpacing = 16.dp,
                pageSize = PageSize.Fixed(180.dp)
            ) { index ->
                ProductImage(
                    url = images[index],
                    contentDescription = "images",
                    modifier = imageModifier
                ) {
                    it.centerCrop()
                }
            }
        }
    }
}