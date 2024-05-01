package br.com.itau.app.home.ui.home.components


import androidx.compose.material.Tab
import androidx.compose.material.TabPosition
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import br.com.itau.app.home.R
import br.com.itau.app.ui_components.theme.GableGreen

enum class HomeTabScreen(val index: Int) {
    INVESTMENT(0),
    WALLET(1)
}

@Composable
fun HomeTab(onTabClicked: (HomeTabScreen) -> Unit) {
    var screen by remember { mutableStateOf(HomeTabScreen.INVESTMENT) }

    fun onTabSelected(tabScreen: HomeTabScreen) {
        screen = tabScreen
        onTabClicked(screen)
    }

    TabRow(
        backgroundColor = Color.White,
        selectedTabIndex = screen.index,
        indicator = { tabPositions ->
            HomeTabIndicator(
                tabPositions[screen.index],
                GableGreen
            )
        }
    ) {
        Tab(
            selected = screen == HomeTabScreen.INVESTMENT,
            onClick = { onTabSelected(HomeTabScreen.INVESTMENT) },
            text = { Text(stringResource(R.string.products)) }
        )
        Tab(
            selected = screen == HomeTabScreen.WALLET,
            onClick = { onTabSelected(HomeTabScreen.WALLET) },
            text = { Text(stringResource(R.string.my_wallet)) }
        )
    }
}

@Composable
fun HomeTabIndicator(
    tabPosition: TabPosition,
    indicatorColor: Color
) {
    TabRowDefaults.Indicator(
        modifier = Modifier.tabIndicatorOffset(tabPosition),
        color = indicatorColor
    )
}
