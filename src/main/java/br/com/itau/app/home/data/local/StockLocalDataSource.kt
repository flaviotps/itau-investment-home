package br.com.itau.app.home.data.local

import br.com.itau.app.home.data.model.InvestmentData
import br.com.itau.app.home.data.repository.InvestmentSource
import br.com.itau.app.network.model.NetworkResult
import br.com.itau.app.network.model.handleLocal

class InvestmentLocalDataSource(
    private val stockLocal: InvestmentLocal
) : InvestmentSource {

    override suspend fun listAssets(): NetworkResult<List<InvestmentData>> {
        return handleLocal { stockLocal.listStocks() }
    }
}
