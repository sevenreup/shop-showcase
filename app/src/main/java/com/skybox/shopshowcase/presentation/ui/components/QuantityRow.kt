package com.skybox.shopshowcase.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp

@Composable
fun QuantityRow(quantity: Int, onIncrement: () -> Unit, onDecrement: () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedIconButton(onClick = onDecrement, enabled = quantity > 1) {
            Icon(Icons.Filled.Remove, contentDescription = "")
        }
        Text(text = quantity.toString())
        OutlinedIconButton(onClick = onIncrement) {
            Icon(Icons.Filled.Add, contentDescription = "")
        }
    }
}