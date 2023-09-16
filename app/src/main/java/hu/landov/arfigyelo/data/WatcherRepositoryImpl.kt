package hu.landov.arfigyelo.data

import android.util.Log
import androidx.lifecycle.map
import hu.landov.arfigyelo.data.domain.Price
import hu.landov.arfigyelo.data.domain.Product
import hu.landov.arfigyelo.data.domain.Favorite
import hu.landov.arfigyelo.data.local.LocalCategory
import hu.landov.arfigyelo.data.local.LocalPrice
import hu.landov.arfigyelo.data.local.LocalUtils
import hu.landov.arfigyelo.data.local.WatcherDao
import hu.landov.arfigyelo.data.remote.RemoteCategoryNode
import hu.landov.arfigyelo.data.remote.RemoteUtils
import hu.landov.arfigyelo.data.remote.WatcherApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

const val NORMAL = "NORMAL"
const val DISCOUNTED = "DISCOUNTED"


//TODO Do not update if you do not have fresh data
//TODO clear before update
//TODO solve ONE-TO-MANY relation in Room
class WatcherRepositoryImpl @Inject constructor(
    private val dao: WatcherDao,
    val apiService: WatcherApiService
) : WatcherRepository {

    override fun getChildCategoriesLive(parent: Int) = dao.getChildCategoriesLive(parent).map {
        it.map { localCategory ->
            LocalUtils.getCategory(localCategory)
        }
    }

    override suspend fun updateCategories() {
        withContext(Dispatchers.IO) {
            val categories = apiService.getCategories().categories
            categories.forEach { node ->
                insertRemoteWithChildren(node, 0)
            }
        }
    }

    private suspend fun insertRemoteWithChildren(node: RemoteCategoryNode, parentId: Int = 0) {

        node.categoryNodes.forEach { child ->
            insertRemoteWithChildren(child, node.id)
        }
        val hasProducts = node.id < 1000 || node.id >= 2000
        dao.insertCategory(LocalCategory(node, parentId, hasProducts))
    }

    private fun getLocalProductsByCategory(parent: Int) = dao.getProductsByCategory(parent)

    override fun getProductById(productId: String) =
        dao.getProductById(productId).map { localProduct ->
            return@map LocalUtils.getProduct(localProduct)
        }

    override fun getProductsByCategory(parent: Int) =
        dao.getProductsByCategory(parent).map { localProducts ->
            val products: MutableList<Product> = mutableListOf()
            localProducts.forEach { localProduct ->
                val product = LocalUtils.getProduct(localProduct)
                products.add(product)
            }
            return@map products.toList()
        }

    //TODO If there's a response clear cache
    override suspend fun updateProducts(parentId: Int) {
        if (parentId in 1..999 || parentId >= 2000) {
            withContext(Dispatchers.IO) {
                val products = apiService.getProductsByParentId(parentId)
                products.products.forEach { remoteProduct ->
                    val isFavorite = getFavorites().contains(remoteProduct.id)
                    dao.insertProduct(
                        RemoteUtils.getLocalProduct(
                            remoteProduct,
                            parentId,
                            isFavorite
                        )
                    )

                    remoteProduct.pricesOfChainStores.forEach { remotePricesOfChainstore ->
                        var discounted = false
                        var normalPrice = 0.0
                        var actualPrice = 0.0
                        var discountRate = 0.0
                        var normalUnitPrice = 0.0
                        var actualUnitPrice = 0.0
                        remotePricesOfChainstore.prices.forEach { remotePrice ->
                            when (remotePrice.type) {
                                NORMAL -> {
                                    normalPrice = remotePrice.amount
                                    normalUnitPrice = remotePrice.unitAmount
                                }

                                DISCOUNTED -> {
                                    actualPrice = remotePrice.amount
                                    actualUnitPrice = remotePrice.unitAmount
                                }
                            }
                        }

                        if (actualPrice == 0.0) actualPrice = normalPrice
                        if (actualUnitPrice == 0.0) actualUnitPrice = normalUnitPrice
                        else {
                            if (actualPrice != normalPrice) {
                                discounted = true
                                discountRate = ((normalPrice - actualPrice) / normalPrice) * 100
                            }
                        }
                        //TODO this mapping could be transfered to constructor?
                        val price = LocalPrice(
                            productId = remoteProduct.id,
                            storeName = remotePricesOfChainstore.name,
                            pricesSameEverywher = remotePricesOfChainstore.priceSameEveryWhere,
                            discounted = discounted,
                            bulk = remoteProduct.bulk,
                            unitTitle = remoteProduct.unit,
                            normalPrice = normalPrice,
                            actualPrice = actualPrice,
                            actualUnitPrice = actualUnitPrice,
                            discountRate = discountRate
                        )
                        dao.insertPrice(price)
                    }
                }
            }
        }
    }

    private suspend fun updateProductsAndChildren(id: Int) {
        withContext(Dispatchers.IO) {
            updateProducts(id)
            val children = dao.getChildCategories(id)
            children.forEach { localCategory ->
                updateProductsAndChildren(localCategory.id)
            }
        }
    }

    override suspend fun updateAllProducts() {
        withContext(Dispatchers.IO) {
            updateProductsAndChildren(0)
        }
    }

    private fun getLocalPricesByProduct(productId: String) = dao.getPricesByProduct(productId)

    override suspend fun getPricesByProduct(productId: String) = withContext(Dispatchers.IO) {
        getLocalPricesByProduct(productId).map { localPrice ->
            val price = Price(localPrice)
            return@map price
        }
    }

    private fun getFavorites(): List<String> = dao.getFavorites().map {
        it.productId
    }

    override fun getFavoritesLive() = dao.getFavoritesLive()

    override fun getFavoriteProductsLive() = dao.getFavoriteProductsLive().map {
        it.map { localProduct ->
            Log.d("MAPPIN", localProduct.toString())
            LocalUtils.getProduct(localProduct)
        }.distinctBy {
            it.id
        }
    }

    override suspend fun toggleFavorite(productId: String) {
        withContext(Dispatchers.IO) {
            val products = dao.getProductsById(productId)
            products.forEach { product ->
                dao.insertProduct(
                    product.copy(isFavorite = !product.isFavorite)
                )
                if (product.isFavorite) {
                    dao.deleteFavorite(productId)
                } else {
                    dao.insertFavorite(Favorite(productId))
                }
            }
        }

    }

}