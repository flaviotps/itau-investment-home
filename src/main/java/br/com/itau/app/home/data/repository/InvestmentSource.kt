package br.com.itau.app.home.data.repository

import br.com.itau.app.home.data.model.InvestmentData
import br.com.itau.app.network.model.NetworkResult

interface InvestmentSource {
    suspend fun listAssets(): NetworkResult<List<InvestmentData>>
}