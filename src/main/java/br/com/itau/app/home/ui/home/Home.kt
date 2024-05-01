package br.com.itau.app.home.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.itau.app.analytics.ItauAnalytics
import br.com.itau.app.home.R
import br.com.itau.app.home.ui.home.components.HomeTab
import br.com.itau.app.home.ui.home.components.HomeTabScreen
import br.com.itau.app.home.ui.home.components.InvestmentTab
import br.com.itau.app.home.ui.home.components.WalletTab
import br.com.itau.app.ui_components.theme.GableGreen


const val SCREEN_HOME_TAB = "SCREEN_HOME_TAB"
const val SCREEN_INVESTMENT_TAB = "SCREEN_INVESTMENT_TAB"
@Composable
fun Home(stocksViewModel: HomeViewModel) {
    val state by stocksViewModel.state.observeAsState(initial = HomeState.Loading)
    var tabScreen by remember { mutableStateOf(HomeTabScreen.INVESTMENT) }

    LaunchedEffect(stocksViewModel) {
        stocksViewModel.listAssets()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                modifier = Modifier
                    .background(GableGreen)
                    .padding(top = 12.dp, bottom = 12.dp)
                    .fillMaxWidth(),
                text = stringResource(id = R.string.itau_corretora),
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                textAlign = TextAlign.Center
            )

            when (val currentState = state) {
                is HomeState.OnInvestmentsLoaded -> {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        HomeTab { tabScreen = it }
                        if (tabScreen == HomeTabScreen.INVESTMENT) {
                            ItauAnalytics.logEvent(ItauAnalytics.SCREEN_NAME, SCREEN_HOME_TAB)
                            InvestmentTab(currentState.investments)
                        } else {
                            ItauAnalytics.logEvent(ItauAnalytics.SCREEN_NAME, SCREEN_INVESTMENT_TAB)
                            WalletTab(currentState.investments)
                        }
                    }
                }

                is HomeState.OnNetworkError -> {
                    val errorMessage = currentState.message
                    Text(text = errorMessage)
                }

                is HomeState.OnExceptionError -> {
                    val exception = currentState.exception
                    Text(text = stringResource(id = R.string.failed_to_load_assets, exception))
                }

                HomeState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(modifier = Modifier.size(50.dp))
                    }
                }
            }
        }
    }
}





