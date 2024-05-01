package br.com.itau.app.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import br.com.itau.app.home.ui.home.HomeState
import br.com.itau.app.home.ui.home.HomeViewModel
import br.com.itau.app.home.data.model.InvestmentData
import br.com.itau.app.home.data.repository.InvestmentsRepository
import br.com.itau.app.network.model.NetworkResult
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var investmentsRepository: InvestmentsRepository
    private lateinit var viewModel: HomeViewModel
    private lateinit var stateObserver: Observer<HomeState>

    @Before
    fun setup() {
        investmentsRepository = mockk()
        stateObserver = mockk(relaxed = true)
        viewModel = HomeViewModel(investmentsRepository)
        viewModel.state.observeForever(stateObserver)
    }

    @Test
    fun `listAssets network error`() = runTest {
        val errorMessage = "Network error"
        val networkResult = NetworkResult.Error<List<InvestmentData>>(400, errorMessage)
        coEvery { investmentsRepository.listAssets() } returns networkResult

        viewModel.listAssets()

        verify {
            stateObserver.onChanged(
                match {
                    it is HomeState.OnNetworkError && it.message == errorMessage
                }
            )
        }
    }


    @Test
    fun `listAssets should return exception error`() =  runTest {

        val errorMessage = "Exception error"
        val networkResult = NetworkResult.Exception<List<InvestmentData>>(Throwable(errorMessage))
        coEvery { investmentsRepository.listAssets() } returns networkResult

        viewModel.listAssets()

        verify {
            stateObserver.onChanged(
                match {
                    it is HomeState.OnExceptionError && it.exception.message == errorMessage
                }
            )
        }
    }

    @Test
    fun `listAssets should return success`() =  runTest {

        val investments = listOf(
            InvestmentData("Petrobras SA", "PETR4", 154f, 14.52, "Ação"),
            InvestmentData("Tesouro Direto NTNB-B", "Tesouro_1", 50f, 11.16, "Tesouro")
        )

        coEvery { investmentsRepository.listAssets() } returns NetworkResult.Success(investments)


        viewModel.listAssets()

        verify {
            stateObserver.onChanged(
                match {
                    it is HomeState.OnInvestmentsLoaded && it.investments == investments
                }
            )
        }
    }
}
