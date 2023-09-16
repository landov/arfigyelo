package hu.landov.arfigyelo.data.remote

data class RemoteProduct(
    val id: String,
    val name: String,
    val imageUrl: String,
    val unit: String,
    val unitTitle: String,
    val bulk: Boolean,
    val packaging: String,
    val categoryPath: String,
    val minUnitPrice: Double,
    val pricesOfChainStores: List<RemotePricesOfChainStore>

)
