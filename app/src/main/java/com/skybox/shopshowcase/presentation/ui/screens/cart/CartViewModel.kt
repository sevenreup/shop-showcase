package com.skybox.shopshowcase.presentation.ui.screens.cart

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skybox.shopshowcase.R
import com.skybox.shopshowcase.data.repository.ICartRepository
import com.skybox.shopshowcase.data.repository.IOrderRepository
import com.skybox.shopshowcase.data.repository.IProductRepository
import com.skybox.shopshowcase.domain.model.Cart
import com.skybox.shopshowcase.domain.model.CartItem
import com.skybox.shopshowcase.domain.model.LoadableState
import com.skybox.shopshowcase.domain.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartRepository: ICartRepository,
    private val orderRepository: IOrderRepository,
    private val productRepository: IProductRepository
) : ViewModel() {
    private val _relatedProducts =
        MutableStateFlow<LoadableState<List<Product>>>(LoadableState.Loading)
    val relatedProducts = _relatedProducts.asStateFlow()

    private val _state = MutableStateFlow<CartScreenState>(CartScreenState.Idle)
    val state = _state.asStateFlow()

    val cartFlow: Flow<Cart> = cartRepository.getCartItems().map { cartItemsWithProduct ->
        val total = calculateTotal(cartItemsWithProduct)
        val cartState = Cart(total, cartItemsWithProduct)
        fetchRelatedProducts(cartItemsWithProduct)
        cartState
    }

    private fun calculateTotal(cartItems: List<CartItem>): Double {
        return cartItems.sumOf { it.quantity * it.price }
    }

    fun placeOrder(cart: Cart) {
        viewModelScope.launch {
            try {
                _state.emit(CartScreenState.IsBusy)
                orderRepository.placeOrder(cart.cartItems, cart.total)
                _state.emit(CartScreenState.OrderPlacedSuccessful)
            } catch (e: Exception) {
                _state.emit(CartScreenState.Error(R.string.order_failed))
            }
        }
    }

    fun incrementCartItem(cartItem: CartItem) {
        viewModelScope.launch {
            try {
                _state.value = CartScreenState.IsBusy
                cartRepository.updateItem(cartItem.productId, cartItem.quantity + 1)
                _state.emit(CartScreenState.CartUpdateSuccessful)
            } catch (e: Exception) {
                _state.value = CartScreenState.Error(R.string.cart_update_failed)
            }
        }
    }

    fun decrementCartItem(cartItem: CartItem) {
        viewModelScope.launch {
            try {
                _state.emit(CartScreenState.IsBusy)
                val quantity = cartItem.quantity - 1
                if (quantity > 0) {
                    cartRepository.updateItem(cartItem.productId, quantity)
                }
                _state.emit(CartScreenState.CartUpdateSuccessful)
            } catch (e: Exception) {
                _state.emit(CartScreenState.Error(R.string.cart_update_failed))
            }
        }
    }

    fun removeFromCart(productId: Int) {
        viewModelScope.launch {
            try {
                _state.emit(CartScreenState.IsBusy)
                cartRepository.removeFromCart(productId)
                _state.emit(CartScreenState.CartUpdateSuccessful)
            } catch (e: Exception) {
                _state.emit(CartScreenState.Error(R.string.cart_update_failed))
            }
        }
    }

    fun clearCart() {
        viewModelScope.launch {
            try {
                _state.emit(CartScreenState.IsBusy)
                cartRepository.clearCartItems()
                _state.emit(CartScreenState.CartUpdateSuccessful)
            } catch (e: Exception) {
                _state.emit(CartScreenState.Error(R.string.cart_update_failed))
            }
        }
    }

    private fun fetchRelatedProducts(cartItemsWithProduct: List<CartItem>) {
        viewModelScope.launch {
            _relatedProducts.emit(LoadableState.Loading)
            val brand = mutableSetOf<String>()
            val productIds = mutableSetOf<Int>()
            val categoryIds = mutableSetOf<Int>()
            var pair = Pair(0.0, 0.0)
            cartItemsWithProduct.forEach {
                brand.add(it.brand)
                productIds.add(it.productId)
                categoryIds.add(it.category.first)
                if (it.price < pair.first) {
                    pair = pair.copy(first = it.price)
                }
                if (it.price > pair.second) {
                    pair = pair.copy(second = it.price)
                }
            }
            val products = productRepository.getRecommendedProducts(
                brands = brand.toList(),
                categories = categoryIds.toList(),
                filterOut = productIds.toList(),
                priceLower = pair.first,
                priceUpper = pair.second,
            )
            _relatedProducts.emit(LoadableState.Success(products))
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