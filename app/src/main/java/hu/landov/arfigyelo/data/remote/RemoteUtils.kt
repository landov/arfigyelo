package hu.landov.arfigyelo.data.remote

import hu.landov.arfigyelo.data.local.LocalProduct

class RemoteUtils private constructor() {
    companion object {
        fun getLocalProduct(
            remoteProduct: RemoteProduct,
            parentId: Int,
            isFavorite: Boolean = false
        ): LocalProduct {
            return LocalProduct(
                id = remoteProduct.id,
                parentId = parentId,
                name = remoteProduct.name,
                imageUrl = remoteProduct.imageUrl,
                unit = remoteProduct.unit,
                unitTitle = remoteProduct.unitTitle,
                bulk = remoteProduct.bulk,
                packaging = remoteProduct.packaging,
                categoryPath = remoteProduct.categoryPath,
                minUnitPrice = remoteProduct.minUnitPrice,
                isFavorite = isFavorite
            )
        }
    }
}