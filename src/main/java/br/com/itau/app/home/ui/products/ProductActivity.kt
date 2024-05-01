package br.com.itau.app.home.ui.products

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import br.com.itau.app.home.data.local.InvestmentLocal
import br.com.itau.app.home.data.local.InvestmentLocalDataSource
import br.com.itau.app.home.data.remote.InvestmentRemoteDataSource
import br.com.itau.app.home.data.remote.InvestmentService
import br.com.itau.app.home.data.repository.InvestmentsRepository
import br.com.itau.app.home.ui.products.components.ProductList
import br.com.itau.app.network.NetworkClient
import br.com.itau.app.ui_components.theme.ItauInvestmentsTheme


class ProductActivity : ComponentActivity() {

    companion object {
        const val ASSET_TYPE_KEY = "ASSET_TYPE_KEY"
        const val ASSET_COLOR_INDEX = "ASSET_COLOR_INDEX"
    }

    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val assetType = intent?.getStringExtra(ASSET_TYPE_KEY)
        val colorIndex = intent?.getIntExtra(ASSET_COLOR_INDEX, 0) ?: 0

        createBackPressDispatcher()

        setContent {

            ItauInvestmentsTheme {
                val scaffoldState = rememberScaffoldState()
                navController = rememberNavController()

                Scaffold(
                    scaffoldState = scaffoldState,
                    topBar = {
                        TopAppBar(
                            title = { Text(text = assetType.orEmpty()) },
                            navigationIcon = {
                                IconButton(onClick = { onBackPressedDispatcher.onBackPressed() }) {
                                    Icon(
                                        Icons.AutoMirrored.Filled.ArrowBack,
                                        contentDescription = "Back"
                                    )
                                }
                            }
                        )
                    },
                    content = { padding ->
                        Column(
                            modifier = Modifier
                                .padding(padding.calculateTopPadding() + 16.dp)
                                .fillMaxSize()
                                .background(Color.White),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            assetType?.let { type ->
                                val investmentRemoteService =
                                    NetworkClient(InvestmentService::class.java)
                                val investmentLocalService = InvestmentLocal(application)
                                val investmentRemoteDataSource =
                                    InvestmentRemoteDataSource(investmentRemoteService)
                                val investmentLocalDataSource =
                                    InvestmentLocalDataSource(investmentLocalService)
                                val investmentsRepository = InvestmentsRepository(
                                    investmentRemoteDataSource,
                                    investmentLocalDataSource
                                )
                                val productViewModel: ProductViewModel = viewModel(
                                    factory = ProductViewModelFactory(investmentsRepository)
                                )
                                ProductList(productViewModel, type, colorIndex)
                            } ?: run {
                                Text(text = "Tipo de ativo não disponível")
                            }
                        }
                    }
                )
            }
        }
    }

    private fun createBackPressDispatcher() {
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (navController.currentBackStackEntry == null) {
                    finish()
                } else {
                    navController.popBackStack()
                }
            }
        }
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }
}
