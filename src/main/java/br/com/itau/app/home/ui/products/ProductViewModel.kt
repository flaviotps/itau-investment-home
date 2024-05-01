package br.com.itau.app.home.ui.products

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.itau.app.home.data.repository.InvestmentsRepository
import br.com.itau.app.network.model.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductViewModel(private val investmentsRepository: InvestmentsRepository) : ViewModel() {

    private val _state = MutableLiveData<ProductState>()
    val state: LiveData<ProductState> = _state

    fun listAssets(type: String) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val response = investmentsRepository.listAssets()) {
                is NetworkResult.Success -> {
                    val responseFilter = response.data.filter { it.type.equals(type, true) }
                    _state.postValue(ProductState.OnInvestmentsLoaded(responseFilter))
                }

                is NetworkResult.Error -> {
                    _state.postValue(ProductState.OnNetworkError(response.message.orEmpty()))
                }

                is NetworkResult.Exception -> {
                    _state.postValue(ProductState.OnExceptionError(response.exception))
                }
            }
        }
    }
}