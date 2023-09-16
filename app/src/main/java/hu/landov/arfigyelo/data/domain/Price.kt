package hu.landov.arfigyelo.data.domain

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import hu.landov.arfigyelo.data.local.LocalPrice


data class Price (
    val storeName : String,
    val pricesSameEverywher: Boolean,
    val normalPrice : Double,
    val discounted: Boolean,
    val discountedPrice : Double,
    val discountRate : Double,
    val bulk : Boolean,
    val unitTitle : String,
    val unitPrice: Double
) {
    constructor(localPrice: LocalPrice) : this(
        storeName = localPrice.storeName,
        pricesSameEverywher = localPrice.pricesSameEverywher,
        discounted = localPrice.discounted,
        normalPrice = localPrice.normalPrice,
        discountedPrice = localPrice.actualPrice,
        discountRate = localPrice.discountRate,
        bulk = localPrice.bulk,
        unitTitle = localPrice.unitTitle,
        unitPrice = localPrice.actualUnitPrice
    )
}
