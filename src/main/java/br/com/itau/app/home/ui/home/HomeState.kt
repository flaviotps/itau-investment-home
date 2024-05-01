package br.com.itau.app.home.ui.home

import br.com.itau.app.home.data.model.InvestmentData

sealed class HomeState {
    data object Loading : HomeState()
    data class OnInvestmentsLoaded(val investments: List<InvestmentData>) : HomeState()
    data class OnNetworkError(val message: String) : HomeState()
    data class OnExceptionError(val exception: Throwable) : HomeState()
}
