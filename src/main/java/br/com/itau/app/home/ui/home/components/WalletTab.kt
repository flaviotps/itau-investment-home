package br.com.itau.app.home.ui.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import br.com.itau.app.home.commons.getColorForIndex
import br.com.itau.app.home.data.model.InvestmentData
import br.com.itau.app.ui_components.card.AssetCard

@Composable
fun WalletTab(investments: List<InvestmentData>) {
    AssetList(investments)
}

@Composable
fun AssetList(investments: List<InvestmentData>) {
    val indexedAssets = investments.groupBy { it.type }

    Column(modifier = Modifier.fillMaxWidth()) {
        indexedAssets.entries.forEachIndexed { index, (_, assets) ->
            val backgroundColor = getColorForIndex(index)
            assets.forEach { asset ->
                AssetCard(
                    name = asset.name,
                    quantity = asset.quantity,
                    unitPrice = asset.unitPrice,
                    titleColor = Color.White,
                    titleBackgroundColor = backgroundColor
                )
            }
        }
    }
}
