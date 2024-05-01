package br.com.itau.app.home.data.repository

import br.com.itau.app.home.data.local.InvestmentLocalDataSource
import br.com.itau.app.home.data.model.InvestmentData
import br.com.itau.app.home.data.remote.InvestmentRemoteDataSource
import br.com.itau.app.network.model.NetworkResult

class InvestmentsRepository(
    private val investmentRemoteDataSource: InvestmentRemoteDataSource,
    private val investmentLocalDataSource: InvestmentLocalDataSource
) {
    suspend fun listAssets(): NetworkResult<List<InvestmentData>> {
        val result = investmentRemoteDataSource.listAssets()
        if (result !is NetworkResult.Success) {
            return investmentLocalDataSource.listAssets()
        }
        return result
    }
}