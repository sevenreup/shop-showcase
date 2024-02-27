package com.skybox.shopshowcase.presentation.ui.screens.cart

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skybox.shopshowcase.R
import com.skybox.shopshowcase.data.repository.CartRepository
import com.skybox.shopshowcase.data.repository.OrderRepository
import com.skybox.shopshowcase.data.repository.ProductRepository
import com.skybox.shopshowcase.domain.Cart
import com.skybox.shopshowcase.domain.CartItem
import com.skybox.shopshowcase.domain.LoadableState
import com.skybox.shopshowcase.domain.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartRepository: CartRepository,
    private val orderRepository: OrderRepository,
    private val productRepository: ProductRepository
) : ViewModel() {
    val relatedProducts = MutableStateFlow<LoadableState<List<Product>>>(LoadableState.Loading)
    val cartFlow: Flow<Cart> = cartRepository.getCartItems().map { cartItemsWithProduct ->
        val total = calculateTotal(cartItemsWithProduct)
        val cartState = Cart(total, cartItemsWithProduct)
        fetchRelatedProducts(cartItemsWithProduct)
        cartState
    }

    val state = MutableStateFlow<CartScreenState>(CartScreenState.Idle)

    private fun calculateTotal(cartItems: List<CartItem>): Double {
        return cartItems.sumOf { it.quantity * it.price }
    }

    fun placeOrder(cart: Cart) {
        viewModelScope.launch {
            try {
                state.emit(CartScreenState.IsBusy)
                orderRepository.placeOrder(cart.cartItems, cart.total)
                state.emit(CartScreenState.OrderPlacedSuccessful)
            } catch (e: Exception) {
                state.emit(CartScreenState.Error(R.string.order_failed))
            }
        }
    }

    fun incrementCartItem(cartItem: CartItem) {
        viewModelScope.launch {
            try {
                state.value = CartScreenState.IsBusy
                cartRepository.updateItem(cartItem.productId, cartItem.quantity + 1)
                state.emit(CartScreenState.CartUpdateSuccessful)
            } catch (e: Exception) {
                state.value = CartScreenState.Error(R.string.cart_update_failed)
            }
        }
    }

    fun decrementCartItem(cartItem: CartItem) {
        viewModelScope.launch {
            try {
                state.emit(CartScreenState.IsBusy)
                val quantity = cartItem.quantity - 1
                if (quantity > 0) {
                    cartRepository.updateItem(cartItem.productId, quantity)
                }
                state.emit(CartScreenState.CartUpdateSuccessful)
            } catch (e: Exception) {
                state.emit(CartScreenState.Error(R.string.cart_update_failed))
            }
        }
    }

    fun removeFromCart(productId: Int) {
        viewModelScope.launch {
            try {
                state.emit(CartScreenState.IsBusy)
                cartRepository.removeFromCart(productId)
                state.emit(CartScreenState.CartUpdateSuccessful)
            } catch (e: Exception) {
                state.emit(CartScreenState.Error(R.string.cart_update_failed))
            }
        }
    }

    fun clearCart() {
        viewModelScope.launch {
            try {
                state.emit(CartScreenState.IsBusy)
                cartRepository.clearCartItems()
                state.emit(CartScreenState.CartUpdateSuccessful)
            } catch (e: Exception) {
                state.emit(CartScreenState.Error(R.string.cart_update_failed))
            }
        }
    }

    private fun fetchRelatedProducts(cartItemsWithProduct: List<CartItem>) {
        viewModelScope.launch {
            relatedProducts.emit(LoadableState.Loading)
            val brand = mutableSetOf<String>()
            var pair = Pair(0.0, 0.0)
            cartItemsWithProduct.forEach {
                brand.add(it.brand)
                if (it.price < pair.first) {
                    pair = pair.copy(first = it.price)
                }
                if (it.price > pair.second) {
                    pair = pair.copy(second = it.price)
                }
            }
            val products = productRepository.getRecommendedProducts(
                brand.toList(),
                pair.first,
                pair.second
            )
            relatedProducts.emit(LoadableState.Success(products))
        }
    }
}

sealed class CartScreenState {
    data object Idle : CartScreenState()
    data object IsBusy : CartScreenState()
    data object CartUpdateSuccessful : CartScreenState()
    data object OrderPlacedSuccessful : CartScreenState()
    data class Error(@StringRes val stringRes: Int) : CartScreenState()
}