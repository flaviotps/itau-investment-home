package br.com.itau.app.home.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.itau.app.home.data.repository.InvestmentsRepository

class HomeViewModelFactory(private val investmentsRepository: InvestmentsRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(investmentsRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
