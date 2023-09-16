package hu.landov.arfigyelo.presentation.common

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.landov.arfigyelo.data.WatcherRepository
import hu.landov.arfigyelo.data.WatcherRepositoryImpl
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: WatcherRepository,
) : ViewModel() {

    var errorState = mutableStateOf(
        ErrorState(
            message = "",
            retryable = false
        )
    )

    private val errorHandler =
        CoroutineExceptionHandler { _, exception ->
            errorState.value = errorState.value.copy(
                //TODO provide reasonable messages
                message = "Szerver nem elérhető. Ellenőrizze az internet kapcsolatot!",
                retryable = true
            )
            exception.printStackTrace()
        }

    init {
        updateAll()
    }

    fun updateAll(){
        //TODO schedule updates
        viewModelScope.launch(errorHandler) {
            errorState.value = errorState.value.copy(message = "Frissítés folyamatban...", retryable = false)
            repository.updateCategories()
            repository.updateAllProducts()
            errorState.value = errorState.value.copy(message = "", retryable = false)

        }
    }

}