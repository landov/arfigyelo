package hu.landov.arfigyelo.presentation.productdetail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import hu.landov.arfigyelo.presentation.common.FavoriteBar
import hu.landov.arfigyelo.presentation.common.ProducPrices
import hu.landov.arfigyelo.presentation.common.ProductImage
import hu.landov.arfigyelo.presentation.common.ProductTitle

@Composable
fun DetailScreen() {
    val viewModel: DetailScreenViewModel = hiltViewModel()
    val state by viewModel.state
    if (state.product != null) {
        Column(modifier = Modifier.padding(8.dp).fillMaxSize()) {
            val product = state.product
            ProductTitle(product = state.product!!, style = MaterialTheme.typography.titleLarge)
            Box(
                modifier = Modifier.fillMaxWidth().aspectRatio(1f),
                contentAlignment = Alignment.Center
            ) {
                ProductImage(uri = state.product?.imageUrl ?: "", contentScale = ContentScale.Fit)
            }
            FavoriteBar(productId = product!!.id, selected = product.isFavorite) { it: String ->
                viewModel.toggleProduct(it)
            }
            ProducPrices(state.product!!.prices)


        }
    } else {
        //TODO product not found
    }
}