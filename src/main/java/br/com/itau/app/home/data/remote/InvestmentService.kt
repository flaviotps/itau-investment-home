package br.com.itau.app.home.data.remote

import br.com.itau.app.home.data.model.InvestmentData
import retrofit2.Response
import retrofit2.http.GET

interface InvestmentService {
    @GET("assets")
    suspend fun listAssets(): Response<List<InvestmentData>>
}