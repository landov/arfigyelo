package hu.landov.arfigyelo.presentation.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import hu.landov.arfigyelo.data.domain.Product

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductCard(
    product: Product,
    onProductClick: (id: String) -> Unit = {},
    onFavoriteClick: (id: String) -> Unit
) {
    var discountRate = 0.0
    product.prices.forEach { price ->
        if (price.discounted && price.discountRate > discountRate) discountRate =
            price.discountRate
    }
    OutlinedCard(
        modifier = Modifier.padding(8.dp).fillMaxWidth(),
        onClick = { onProductClick(product.id) }) {
        Row {
            Box(
                modifier = Modifier.weight(0.35f)

            ) {
                val uri = product.imageUrl
                Box(modifier = Modifier.aspectRatio(1f), contentAlignment = Alignment.Center) {
                    ProductImage(uri)
                }
                if (discountRate > 0.0)
                    Surface(
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(4.dp)
                    ) {
                        Text(
                            text = " - ${discountRate.toInt().toString()}%",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onError,
                            modifier = Modifier.padding(4.dp)
                        )
                    }
            }
            Box(modifier = Modifier.weight(0.65f)) {
                Column {
                    ProductTitle(product)
                    val priceList = product.prices ?: emptyList()
                    ProducPrices(priceList)
                    FavoriteBar(product.id, product.isFavorite, onFavoriteClick)
                }
            }
        }

    }

}
