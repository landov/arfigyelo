package hu.landov.arfigyelo.data

import androidx.lifecycle.LiveData
import hu.landov.arfigyelo.data.domain.Category
import hu.landov.arfigyelo.data.domain.Price
import hu.landov.arfigyelo.data.domain.Product
import hu.landov.arfigyelo.data.domain.Favorite

interface WatcherRepository {

    fun getChildCategoriesLive(parent: Int) : LiveData<List<Category>>

    fun getProductById(productId: String): LiveData<Product>
    fun getProductsByCategory(parent: Int): LiveData<List<Product>>

    suspend fun getPricesByProduct(productId: String): List<Price>

    fun getFavoritesLive(): LiveData<List<Favorite>>
    fun getFavoriteProductsLive(): LiveData<List<Product>>

    suspend fun updateCategories()
    suspend fun updateProducts(parentId: Int)
    suspend fun updateAllProducts()
    suspend fun toggleFavorite(productId: String)
}