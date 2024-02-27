package com.skybox.shopshowcase.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.skybox.shopshowcase.data.entities.OrderEntity
import com.skybox.shopshowcase.data.entities.OrderItemEntity
import com.skybox.shopshowcase.data.entities.OrderWithItems
import com.skybox.shopshowcase.domain.CartItem
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderDao {
    @Insert
    suspend fun insertOrder(order: OrderEntity): Long

    @Insert
    suspend fun insertOrderItems(orderItems: List<OrderItemEntity>)

    @Query("DELETE FROM cart_item")
    suspend fun clearCart()

    @Transaction
    suspend fun createOrder(order: OrderEntity,orderItems: List<CartItem>) {
        val orderId = insertOrder(order)
        val items = orderItems.map {
            OrderItemEntity(
                orderId = orderId,
                productId = it.productId,
                quantity = it.quantity,
                price = it.price
            )
        }
        insertOrderItems(items)
        clearCart()
    }

    @Transaction
    @Query("SELECT * FROM orders")
    fun getAllOrdersWithItems(): Flow<List<OrderWithItems>>
}