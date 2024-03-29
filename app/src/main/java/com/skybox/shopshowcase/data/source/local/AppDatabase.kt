package com.skybox.shopshowcase.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.skybox.shopshowcase.data.entities.CartItemEntity
import com.skybox.shopshowcase.data.entities.CategoryEntity
import com.skybox.shopshowcase.data.entities.OrderEntity
import com.skybox.shopshowcase.data.entities.OrderItemEntity
import com.skybox.shopshowcase.data.entities.ProductEntity
import com.skybox.shopshowcase.data.source.local.converters.Converters
import com.skybox.shopshowcase.workers.DatabaseSeederWorker

@Database(
    entities = [ProductEntity::class, CategoryEntity::class, CartItemEntity::class, OrderEntity::class, OrderItemEntity::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun cartDao(): CartDao

    abstract fun orderDao(): OrderDao

    companion object {
        private const val DATABASE_NAME = "shop-showcase"
        const val KEY_FILENAME = "db-file-key"
        const val PLANT_DATA_FILENAME = "seed-data.json"

        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        val request =
                            OneTimeWorkRequestBuilder<DatabaseSeederWorker>().setInputData(
                                workDataOf(KEY_FILENAME to PLANT_DATA_FILENAME)
                            ).build()
                        WorkManager.getInstance(context).enqueue(request)
                    }
                }).build()
        }
    }
}