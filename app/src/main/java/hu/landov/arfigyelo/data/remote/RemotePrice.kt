package hu.landov.arfigyelo.data.remote



data class RemotePrice (
    val type: String, //TODO maybe enum?
    val amount: Double,
    val unitAmount: Double,
    val sameAmountEverywhere: Boolean
)