package br.com.itau.app.home.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.itau.app.home.data.repository.InvestmentsRepository
import br.com.itau.app.network.model.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(private val investmentsRepository: InvestmentsRepository) : ViewModel() {

    private val _state = MutableLiveData<HomeState>()
    val state: LiveData<HomeState> = _state

    fun listAssets() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val response = investmentsRepository.listAssets()) {
                is NetworkResult.Success -> {
                    _state.postValue(HomeState.OnInvestmentsLoaded(response.data))
                }

                is NetworkResult.Error -> {
                    _state.postValue(HomeState.OnNetworkError(response.message.orEmpty()))
                }

                is NetworkResult.Exception -> {
                    _state.postValue(HomeState.OnExceptionError(response.exception))
                }
            }
        }
    }
}