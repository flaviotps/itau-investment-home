package br.com.itau.app.home.ui.home.components

import ProductCarousel
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import br.com.itau.app.home.ui.products.ProductActivity
import br.com.itau.app.home.commons.getColorForIndex
import br.com.itau.app.home.data.model.InvestmentData
import br.com.itau.app.ui_components.card.ExtractCard
import br.com.itau.app.ui_components.carousel.Product
import br.com.itau.app.ui_components.commons.format
import br.com.itau.app.ui_components.commons.formatCurrency
import br.com.itau.app.ui_components.commons.dateToday
import br.com.itau.app.ui_components.graph.PieChart
import br.com.itau.app.ui_components.graph.PieChartInput

@Composable
fun InvestmentTab(investments: List<InvestmentData>) {
    val investmentsByType = investments.groupBy { it.type }.toList()

    val pieChartInput = calculatePieChartInputs(investmentsByType)
    val products = calculateProducts(investmentsByType)

    Box(
        modifier = Modifier
            .padding(top = 32.dp, bottom = 16.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        PieChart(
            radius = 400f,
            innerRadius = 300f,
            transparentWidth = 16f,
            modifier = Modifier
                .width(300.dp)
                .aspectRatio(1f),
            input = pieChartInput
        )
    }
    ProductCarousel(
        modifier = Modifier
            .padding(top = 16.dp),
        products = products
    )

    Column(modifier = Modifier
        .padding(top = 24.dp)
        .padding(horizontal = 50.dp)) {
        Text(text = dateToday(), fontWeight = FontWeight.Bold)
        for (i in 1..5) {
            ExtractCard(
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 0.dp)
            )
            Divider(thickness = 1.dp)
        }
    }
}

private fun calculatePieChartInputs(investmentsByType: List<Pair<String, List<InvestmentData>>>): List<PieChartInput> {
    val pieChartInputs = mutableListOf<PieChartInput>()

    investmentsByType.forEachIndexed { index, (type, dataList) ->
        val productTotal = dataList.sumOf { it.unitPrice * it.quantity }.toFloat()

        val pieChart = PieChartInput(
            color = getColorForIndex(index),
            value = productTotal.toInt(),
            description = type
        )
        pieChartInputs.add(pieChart)
    }

    return pieChartInputs
}

@Composable
private fun calculateProducts(investmentsByType: List<Pair<String, List<InvestmentData>>>): List<Product> {

    val context = LocalContext.current

    val totalAssets =
        investmentsByType.flatMap { it.second }.sumOf { it.unitPrice * it.quantity }.toFloat()

    return investmentsByType.mapIndexed { index, (type, dataList) ->
        val productTotal = dataList.sumOf { it.unitPrice * it.quantity }.toFloat()
        val percentage = (productTotal / totalAssets * 100)

        Product(
            productName = type,
            topLeftText = "${percentage.format()}%",
            balanceText = productTotal.formatCurrency(),
            getColorForIndex(index)
        ) {
            launchActivity(context, type, index)
        }
    }
}

private fun launchActivity(context: Context, assetType: String, colorIndex: Int) {
    val intent = Intent(context, ProductActivity::class.java).apply {
        putExtra(ProductActivity.ASSET_TYPE_KEY, assetType)
        putExtra(ProductActivity.ASSET_COLOR_INDEX, colorIndex)
    }
    context.startActivity(intent)
}

