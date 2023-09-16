package hu.landov.arfigyelo.data.remote

data class RemotePricesOfChainStore (
    val id: String,
    val name: String,
    val priceSameEveryWhere: Boolean,
    val productMinAmount: Double,
    val prices: List<RemotePrice>
)