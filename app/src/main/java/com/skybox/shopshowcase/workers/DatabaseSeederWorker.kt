package com.skybox.shopshowcase.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.skybox.shopshowcase.data.entities.CategoryEntity
import com.skybox.shopshowcase.data.entities.ProductCategoryCrossRef
import com.skybox.shopshowcase.data.entities.ProductEntity
import com.skybox.shopshowcase.data.source.local.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DatabaseSeederWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            val filename = inputData.getString(AppDatabase.KEY_FILENAME)
            if (filename != null) {
                applicationContext.assets.open(filename).use { inputStream ->
                    JsonReader(inputStream.reader()).use { jsonReader ->
                        val seedType = object : TypeToken<SeederData>() {}.type
                        val seedData: SeederData = Gson().fromJson(jsonReader, seedType)

                        val productDao = AppDatabase.getInstance(applicationContext).productDao()

                        productDao.insertCategories(seedData.categories)
                        productDao.upsertAll(seedData.products)
                        productDao.insertProductCategoryCrossRef(seedData.productCategoryCrossRefs)

                        Result.success()
                    }
                }
            } else {
                Log.e(TAG, "Error seeding database - no valid filename")
                Result.failure()
            }
        } catch (ex: Exception) {
            Log.e(TAG, "Error seeding database", ex)
            Result.failure()
        }
    }

    companion object {
        private const val TAG = "SeedDatabaseWorker"
    }
}

data class SeederData(
    val products: List<ProductEntity>,
    val categories: List<CategoryEntity>,
    val productCategoryCrossRefs: List<ProductCategoryCrossRef>
)