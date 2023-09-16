package hu.landov.arfigyelo.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "products", primaryKeys = ["id","parent_id"])
data class LocalProduct(
    val id: String,
    @ColumnInfo(name = "parent_id")
    val parentId : Int,
    val name: String,
    @ColumnInfo(name = "image_url")
    val imageUrl: String,
    val unit: String,
    @ColumnInfo(name = "unit_title")
    val unitTitle: String,
    val bulk: Boolean,
    val packaging: String,
    @ColumnInfo(name = "category_path")
    val categoryPath: String,
    @ColumnInfo(name = "min_unit_price")
    val minUnitPrice: Double,
    @ColumnInfo(name = "is_favorite")
    val isFavorite: Boolean = false
)

