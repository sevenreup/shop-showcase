package com.skybox.shopshowcase.presentation.ui.screens.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skybox.shopshowcase.data.repository.CartRepository
import com.skybox.shopshowcase.data.repository.OrderRepository
import com.skybox.shopshowcase.domain.Cart
import com.skybox.shopshowcase.domain.CartItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartRepository: CartRepository,
    private val orderRepository: OrderRepository
) : ViewModel() {

    val cartState: Flow<Cart> = cartRepository.getCartItems().map { cartItemsWithProduct ->
        val total = calculateTotal(cartItemsWithProduct)
        val cartState = Cart(total, cartItemsWithProduct)

        cartState
    }

    private fun calculateTotal(cartItems: List<CartItem>): Double {
        return cartItems.sumOf { it.quantity * it.price }
    }

    fun placeOrder(cart: Cart) {
        viewModelScope.launch {
            orderRepository.placeOrder(cart.cartItems, cart.total)
        }
    }

    fun incrementCartItem(cartItem: CartItem) {
        viewModelScope.launch {
            cartRepository.updateItem(cartItem.productId, cartItem.quantity + 1)
        }
    }

    fun decrementCartItem(cartItem: CartItem) {
        viewModelScope.launch {
            val quantity = cartItem.quantity - 1
            if (quantity > 0) {
                cartRepository.updateItem(cartItem.productId, quantity)
            }
        }
    }

    fun removeFromCart(productId: Int) {
        viewModelScope.launch {
            cartRepository.removeFromCart(productId)
        }
    }

    fun clearCart() {
        viewModelScope.launch {
            cartRepository.clearCartItems()
        }
    }
}