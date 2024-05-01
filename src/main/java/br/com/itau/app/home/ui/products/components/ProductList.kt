package br.com.itau.app.home.ui.products.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import br.com.itau.app.home.R
import br.com.itau.app.home.commons.getColorForIndex
import br.com.itau.app.home.data.model.InvestmentData
import br.com.itau.app.home.ui.home.HomeState
import br.com.itau.app.home.ui.products.ProductState
import br.com.itau.app.home.ui.products.ProductViewModel
import br.com.itau.app.ui_components.card.AssetCard

@Composable
fun ProductList(productViewModel: ProductViewModel, type: String, colorIndex: Int) {

    val state by productViewModel.state.observeAsState(initial = HomeState.Loading)

    LaunchedEffect(productViewModel) {
        productViewModel.listAssets(type)
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when (val currentState = state) {
            is ProductState.OnInvestmentsLoaded -> {
                SingleTypeAssetList(currentState.investments, colorIndex)
            }

            is ProductState.OnNetworkError -> {
                val errorMessage = currentState.message
                Text(text = errorMessage)
            }

            is ProductState.OnExceptionError -> {
                val exception = currentState.exception
                Text(text = stringResource(id = R.string.failed_to_load_assets, exception))
            }

            ProductState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.size(50.dp))
            }
        }
    }
}


@Composable
fun SingleTypeAssetList(investments: List<InvestmentData>, colorIndex: Int) {
    val indexedAssets = investments.groupBy { it.type }

    Column(modifier = Modifier.fillMaxWidth()) {
        indexedAssets.entries.forEachIndexed { _, (_, assets) ->
            val backgroundColor = getColorForIndex(colorIndex)
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
