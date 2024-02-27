package com.skybox.shopshowcase.presentation.ui.screens.orders

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.skybox.shopshowcase.R
import com.skybox.shopshowcase.domain.Order
import com.skybox.shopshowcase.presentation.ui.components.ProductImage
import com.skybox.shopshowcase.util.format
import com.skybox.shopshowcase.util.formatCurrency

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrdersScreen(navigateBack: () -> Unit, viewModel: OrdersScreenViewModel = hiltViewModel()) {
    val orders by viewModel.orders.collectAsState(listOf())

    Scaffold(topBar = {
        TopAppBar(title = { Text(text = stringResource(id = R.string.orders)) }, navigationIcon = {
            IconButton(
                onClick = navigateBack
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "")
            }
        })
    }) { paddingValues ->
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            items(orders) { item ->
                OrderItem(item)
            }
        }
    }
}

@Composable
fun OrderItem(item: Order) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Text(text = item.orderDate.format())
            Text(text = item.totalAmount.formatCurrency())
            Spacer(modifier = Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                item.items.forEach {
                    ProductImage(
                        url = it.productImage, modifier = Modifier
                            .size(80.dp)
                            .clip(
                                RoundedCornerShape(6.dp)
                            )
                    )
                }
            }
        }
    }
}