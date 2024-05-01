package br.com.itau.app.home.ui.products

import br.com.itau.app.home.data.model.InvestmentData

sealed class ProductState {
    data object Loading : ProductState()
    data class OnInvestmentsLoaded(val investments: List<InvestmentData>) : ProductState()
    data class OnNetworkError(val message: String) : ProductState()
    data class OnExceptionError(val exception: Throwable) : ProductState()
}
