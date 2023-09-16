package hu.landov.arfigyelo.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "prices", primaryKeys = ["product_id","store_name"])
data class LocalPrice(
    @ColumnInfo(name = "product_id")
    val productId : String,
    @ColumnInfo(name = "store_name")
    val storeName : String,
    @ColumnInfo(name = "prices_same_everywhere")
    val pricesSameEverywher: Boolean = true,
    val discounted: Boolean,
    val bulk: Boolean = false,
    @ColumnInfo(name = "unit_title")
    val unitTitle : String = "",
    @ColumnInfo(name = "normal_price")
    val normalPrice : Double,
    @ColumnInfo(name = "actual_price")
    val actualPrice : Double,
    @ColumnInfo
    val actualUnitPrice : Double,
    @ColumnInfo(name = "discount_rate")
    val discountRate : Double = 0.0,

)