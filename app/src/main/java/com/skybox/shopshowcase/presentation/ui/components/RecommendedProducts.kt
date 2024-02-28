package com.skybox.shopshowcase.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.skybox.shopshowcase.domain.model.LoadableState
import com.skybox.shopshowcase.domain.model.Product
import com.skybox.shopshowcase.util.formatCurrency

@Composable
fun RecommendedProducts(
    onClick: (productId: Int) -> Unit,
    relatedState: LoadableState<List<Product>>
) {
    if (relatedState is LoadableState.Success) {
        Text(text = "Recommended", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow(
            contentPadding = PaddingValues(8.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            val items = relatedState.data
            items(items) { product ->
                Card(onClick = {
                    onClick(product.productId)
                }) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.padding(12.dp)
                    ) {
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