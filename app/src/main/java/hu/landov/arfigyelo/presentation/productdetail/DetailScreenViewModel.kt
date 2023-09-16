package hu.landov.arfigyelo.presentation.productdetail

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.landov.arfigyelo.data.WatcherRepository
import hu.landov.arfigyelo.data.WatcherRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailScreenViewModel @Inject constructor(
    val repository: WatcherRepository,
    stateHandle: SavedStateHandle
) : ViewModel() {
    val productId = stateHandle.get<String>("product_id")

    val state = mutableStateOf(
        DetailScreenState(product = null)
    )

    init {
        repository.getProductById(productId ?: "").observeForever {
            state.value = state.value.copy( product = it)
            updateProductPrices()
        }
    }

    private fun updateProductPrices(){
        viewModelScope.launch(Dispatchers.IO) {
            val prices = repository.getPricesByProduct(productId ?: "")
            state.value = state.value.copy(product = state.value.product?.copy(prices = prices))
        }
    }

    fun toggleProduct(producId: String){
        viewModelScope.launch(Dispatchers.IO) {
            repository.toggleFavorite(producId)
        }
    }
}