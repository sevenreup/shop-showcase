package com.skybox.shopshowcase.presentation.ui.screens.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skybox.shopshowcase.data.entities.CartItemWithProduct
import com.skybox.shopshowcase.data.repository.CartRepository
import com.skybox.shopshowcase.domain.Cart
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(private val cartRepository: CartRepository) : ViewModel() {

    val cartState: Flow<Cart> = cartRepository.getCartItems().map { cartItemsWithProduct ->
        val total = calculateTotal(cartItemsWithProduct)
        val cartState = Cart(total, cartItemsWithProduct)

        cartState
    }

    private fun calculateTotal(cartItems: List<CartItemWithProduct>): Double {
        return cartItems.sumOf { it.cartItem.quantity * it.product.price }
    }

    fun addToCart(productId: Int, quantity: Int) {
        viewModelScope.launch {
            cartRepository.addToCart(productId, quantity)
        }
    }

    fun removeFromCart(productId: Int) {
        viewModelScope.launch {
            cartRepository.removeFromCart(productId)
        }
    }
}