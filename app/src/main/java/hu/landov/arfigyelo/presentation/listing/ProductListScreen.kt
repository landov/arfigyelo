package hu.landov.arfigyelo.presentation.listing

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import hu.landov.arfigyelo.data.domain.Category
import hu.landov.arfigyelo.data.domain.Product
import hu.landov.arfigyelo.presentation.common.ProductCard


@Composable
fun CategoryScreen(
    navController: NavController
) {
    val viewModel: ProductListViewModel = hiltViewModel()
    val state by viewModel.state


    Column() {
        val onItemClick: (id: Int) -> Unit =
            { id -> navController.navigate("categories/$id") }
        val onProductClick: (id: String) -> Unit =
            { id -> navController.navigate("product/$id") }
        val onFavoriteClick: (id: String) -> Unit =
            {id -> viewModel.toggleProduct(id)}
        if (state.error != "") {
            Text(
                text = state.error,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.error
            )
        }
        if (state.hasProducts) {
            CategoryProductListScreen(
                state.categories,
                state.products,
                onItemClick,
                onProductClick,
                onFavoriteClick
            )

        } else {
            val onFavoritesClick = {navController.navigate("favorites")}
            Row(
                modifier = Modifier.fillMaxHeight(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(modifier = Modifier.weight(0.15f)) {}
                Box(modifier = Modifier.weight(0.7f)) {
                    CategoryListScreen(state.categories, onItemClick, onFavoritesClick, state.hasFavorites)
                }
                Box(modifier = Modifier.weight(0.15f)) {}
            }
        }

    }


}

@Composable
fun CategoryListScreen(
    categories: List<Category>,
    onItemClick: (id: Int) -> Unit = {},
    onFavoritesClick: () -> Unit = {},
    showFavorite: Boolean = false
) {
    Column() {
        categories.forEach { node ->
            CategoryItem(node, onItemClick)
        }
        if(showFavorite)
            FavoritesButton(
                onClick = onFavoritesClick
            )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CategoryProductListScreen(
    categories: List<Category>,
    products: List<Product>,
    onCategoryClick: (id: Int) -> Unit = {},
    onProductClick: (id: String) -> Unit = {},
    onFavoriteClick: (id: String) -> Unit
) {

    FlowRow(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth().padding(0.dp)
    ) {
        categories.forEach { node ->
            SubCategoryItem(node, onCategoryClick)
        }
    }

    LazyColumn() {
        items(products) { product ->
            ProductCard(product, onProductClick, onFavoriteClick)

        }
    }

    if(products.isEmpty()){
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
            Text(text = "Nincs megjeleníthető termék.", style = MaterialTheme.typography.titleLarge)

        }
    }
}

@Composable
fun SubCategoryItem(category: Category, onItemClick: (id: Int) -> Unit) {
    OutlinedButton(
        modifier = Modifier.padding(
            paddingValues = PaddingValues(
                vertical = 2.dp,
                horizontal = 4.dp
            )
        ),
        onClick = { onItemClick(category.id) },
        //  label = {

        //    }
    ) {
        Text(
            text = category.name
        )
    }
}

@Composable
fun CategoryItem(category: Category, onItemClick: (id: Int) -> Unit) {
    Button(onClick = { onItemClick(category.id) }, modifier = Modifier.fillMaxWidth()) {
        Text(text = category.name)
    }
}

@Composable
fun FavoritesButton(onClick : () -> Unit){
    Button(onClick = onClick, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary) ) {
        Text(text = "Kedvencek", color = MaterialTheme.colorScheme.onSecondary)
    }
}




