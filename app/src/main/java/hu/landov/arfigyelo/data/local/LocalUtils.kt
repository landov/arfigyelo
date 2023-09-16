package hu.landov.arfigyelo.data.local

import hu.landov.arfigyelo.data.domain.Category
import hu.landov.arfigyelo.data.domain.Price
import hu.landov.arfigyelo.data.domain.Product

class LocalUtils private constructor() {

    companion object{

        fun getProduct(localProduct: LocalProduct) : Product {
            return Product(
                id = localProduct.id,
                parentId = localProduct.parentId,
                name = localProduct.name,
                imageUrl = localProduct.imageUrl,
                unit = localProduct.unit,
                unitTitle = localProduct.unitTitle,
                bulk = localProduct.bulk,
                packaging = localProduct.packaging,
                minUnitPrice = localProduct.minUnitPrice,
                prices = listOf<Price>(),
                isFavorite = localProduct.isFavorite
            )
        }

        fun getCategory(localCategory: LocalCategory) : Category {
            return Category(
                id = localCategory.id,
                parentId = localCategory.parentId,
                name = localCategory.name,
                path = localCategory.path,
                icon = localCategory.icon,
                hasProducts = localCategory.hasProducts
            )
        }
    }
}