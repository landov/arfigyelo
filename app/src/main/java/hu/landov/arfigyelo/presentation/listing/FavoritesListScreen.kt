package hu.landov.arfigyelo.presentation.listing

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import hu.landov.arfigyelo.presentation.common.ProductCard

@Composable
fun FavoritesListScreen(
    navController: NavController
) {
    val viewModel: FavoritesListViewModel = hiltViewModel()
    val state by viewModel.state

    val onProductClick: (id: String) -> Unit =
        { id -> navController.navigate("product/$id") }
    val onFavoriteClick: (id: String) -> Unit =
        { id -> viewModel.toggleProduct(id) }

    LazyColumn() {
        items(state.products) { product ->
            ProductCard(product, onProductClick, onFavoriteClick)

        }
    }

}