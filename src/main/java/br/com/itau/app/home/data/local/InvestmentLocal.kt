package br.com.itau.app.home.data.local

import android.app.Application
import br.com.itau.app.home.data.model.InvestmentData
import br.com.itau.app.network.loadFromAssets
import com.google.gson.reflect.TypeToken


class InvestmentLocal(private val application: Application) {
    private val typeToken = object : TypeToken<List<InvestmentData>>() {}
    fun listStocks(): List<InvestmentData> = loadFromAssets<List<InvestmentData>>(application, "assets.json", typeToken) ?: emptyList()
}