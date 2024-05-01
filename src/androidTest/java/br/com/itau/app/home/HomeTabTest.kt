package br.com.itau.app.home

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import br.com.itau.app.home.ui.home.components.HomeTab
import br.com.itau.app.home.ui.home.components.HomeTabScreen
import org.junit.Rule
import org.junit.Test

class HomeTabTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun whenTabWalletClicked_setHomeTabScreenWallet() {
        var selectedTab: HomeTabScreen? = null

        composeTestRule.setContent {
            HomeTab {
                selectedTab = it
            }
        }

        composeTestRule.onNodeWithText("Minha carteira")
            .performClick()

        assert(selectedTab == HomeTabScreen.WALLET)
    }

    @Test
    fun whenTabInvestmentClicked_setHomeTabScreenInvestment() {
        var selectedTab: HomeTabScreen? = null

        composeTestRule.setContent {
            HomeTab {
                selectedTab = it
            }
        }

        composeTestRule.onNodeWithText("Produtos")
            .performClick()

        assert(selectedTab == HomeTabScreen.INVESTMENT)
    }
}
