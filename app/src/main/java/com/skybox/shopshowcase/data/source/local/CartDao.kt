package com.skybox.shopshowcase.data.source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.skybox.shopshowcase.data.entities.CartItemEntity
import com.skybox.shopshowcase.data.entities.CartItemWithProduct
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartItem(cartItem: CartItemEntity)

    @Delete
    suspend fun deleteCartItem(cartItem: CartItemEntity)

    @Transaction
    @Query("SELECT * FROM cart_item WHERE productId = :productId")
    suspend fun getCartItem(productId: Int): CartItemWithProduct?

    @Transaction
    @Query("SELECT * FROM cart_item")
    fun getAllCartItemsWithProduct(): Flow<List<CartItemWithProduct>>
}