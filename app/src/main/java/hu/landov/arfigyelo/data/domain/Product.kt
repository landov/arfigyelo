package hu.landov.arfigyelo.data.domain

import androidx.lifecycle.LiveData
import androidx.room.ColumnInfo
import hu.landov.arfigyelo.data.local.LocalProduct

data class Product(
    val id: String,
    val parentId: Int,
    val name: String,
    val imageUrl: String,
    val unit: String,
    val unitTitle: String,
    val bulk: Boolean,
    val packaging: String,
    val minUnitPrice: Double,
    var prices: List<Price>,
    var isFavorite: Boolean = false

)