package hu.landov.arfigyelo.presentation.listing

import hu.landov.arfigyelo.data.domain.Category
import hu.landov.arfigyelo.data.domain.Product

data class ProductListScreenState (
    val hasProducts: Boolean,
    val categories : List<Category>,
    val products: List<Product>,
    val error: String = "",
    val hasFavorites : Boolean
)
