package hu.landov.arfigyelo.presentation.listing

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.landov.arfigyelo.data.WatcherRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val repository: WatcherRepository,
    stateHandle: SavedStateHandle
) : ViewModel() {

    //TODO make all mutableStates private
    // TODO stateList?
    var state = mutableStateOf(
        ProductListScreenState(
            hasProducts = false,
            categories = emptyList(),
            products = emptyList(),
            hasFavorites = false
        )
    )

    private val errorHandler =
        CoroutineExceptionHandler { _, exception ->
            state.value = state.value.copy(
                error = exception.message.toString()
            )
            exception.printStackTrace()
        }

    private val parent_id = stateHandle.get<Int>("parent_id") ?: 0

    init {
        state.value = state.value.copy(
            hasProducts = (parent_id in 1..999) || parent_id >= 2000
        )
        repository.getChildCategoriesLive(parent_id).observeForever {
            state.value = state.value.copy(
                categories = it
            )
        }
        repository.getProductsByCategory(parent_id).observeForever {
            //TODO this should be solved in different way!
            viewModelScope.launch {
                it.forEach { product ->
                    product.prices = repository.getPricesByProduct(product.id)
                }
                state.value = state.value.copy(
                    products = it
                )
            }
        }
        repository.getFavoritesLive().observeForever {
            state.value = state.value.copy(hasFavorites = it.isNotEmpty())
        }
     /*   viewModelScope.launch(errorHandler) {
            repository.updateProducts(parent_id)
        }*/
    }

    fun toggleProduct(producId: String){
        viewModelScope.launch(Dispatchers.IO) {
            repository.toggleFavorite(producId)
        }
    }


}