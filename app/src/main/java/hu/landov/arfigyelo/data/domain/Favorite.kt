package hu.landov.arfigyelo.data.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
class Favorite (
    @PrimaryKey
    @ColumnInfo(name = "product_id")
    val productId : String
)