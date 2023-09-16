package hu.landov.arfigyelo.presentation.listing

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.landov.arfigyelo.data.WatcherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesListViewModel @Inject constructor(
    private val repository: WatcherRepository
) : ViewModel() {

    //TODO mutableStateList?
    var state = mutableStateOf(
        FavoritesListScreenState(
            products = emptyList()
        )
    )

    init {
        repository.getFavoriteProductsLive().observeForever {
            //TODO duplication
            viewModelScope.launch {
                it.forEach {product ->
                    product.prices = repository.getPricesByProduct(product.id)
                }
                state.value = state.value.copy(
                    products = it
                )
            }

        }
    }

    fun toggleProduct(producId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.toggleFavorite(producId)
        }
    }

}