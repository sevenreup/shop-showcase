package com.skybox.shopshowcase.presentation.ui.screens.orders

import androidx.lifecycle.ViewModel
import com.skybox.shopshowcase.data.repository.OrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OrdersScreenViewModel @Inject constructor(orderRepository: OrderRepository) : ViewModel() {
    val orders = orderRepository.getAllOrdersWithItems()
}