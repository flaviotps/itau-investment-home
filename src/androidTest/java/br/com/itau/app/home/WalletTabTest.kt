package br.com.itau.app.home

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onRoot
import br.com.itau.app.home.ui.home.components.AssetList
import br.com.itau.app.home.data.model.InvestmentData
import org.junit.Rule
import org.junit.Test

class WalletTabTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun assetList_RenderCorrectNumberOfAssetCards() {
        val investments = listOf(
            InvestmentData("Type1", "Asset1", 1f, 100.0, "ACAO"),
            InvestmentData("Type1", "Asset2", 1f, 200.0, "ACAO"),
            InvestmentData("Type2", "Asset3", 1f, 300.0, "ACAO"),
        )

        composeTestRule.setContent {
            AssetList(investments)
        }

        composeTestRule.onRoot().onChildren().assertCountEquals(3)
    }
}
