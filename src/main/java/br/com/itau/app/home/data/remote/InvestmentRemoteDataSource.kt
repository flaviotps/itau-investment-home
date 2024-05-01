package br.com.itau.app.home.data.remote

import br.com.itau.app.home.data.model.InvestmentData
import br.com.itau.app.network.model.NetworkResult
import br.com.itau.app.network.model.handleApi
import br.com.itau.app.home.data.repository.InvestmentSource

class InvestmentRemoteDataSource(
    private val investmentService: InvestmentService
) : InvestmentSource {
    override suspend fun listAssets(): NetworkResult<List<InvestmentData>> {
        return handleApi {
            investmentService.listAssets()
        }
    }
}
