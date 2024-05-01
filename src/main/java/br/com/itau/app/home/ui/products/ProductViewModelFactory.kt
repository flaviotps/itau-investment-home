package br.com.itau.app.home.ui.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.itau.app.home.data.repository.InvestmentsRepository

class ProductViewModelFactory(private val investmentsRepository: InvestmentsRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProductViewModel(investmentsRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
