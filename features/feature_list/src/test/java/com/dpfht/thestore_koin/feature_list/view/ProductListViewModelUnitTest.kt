package com.dpfht.thestore_koin.feature_list.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import com.dpfht.thestore_koin.domain.entity.DataDomain
import com.dpfht.thestore_koin.domain.entity.DataEntity
import com.dpfht.thestore_koin.domain.entity.ProductEntity
import com.dpfht.thestore_koin.domain.entity.Result
import com.dpfht.thestore_koin.domain.usecase.GetProductsUseCase
import com.dpfht.thestore_koin.feature_list.adapter.ProductListAdapter
import com.dpfht.thestore_koin.framework.data.datasource.local.onlinechecker.OnlineChecker
import com.dpfht.thestore_koin.framework.ext.toRupiahString
import com.dpfht.thestore_koin.framework.navigation.NavigationService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.eq
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class ProductListViewModelUnitTest {

  private val testDispatcher = UnconfinedTestDispatcher()

  @get:Rule
  val taskExecutorRule = InstantTaskExecutorRule()

  private lateinit var viewModel: ProductListViewModel

  @Mock
  private lateinit var getProductsUseCase: GetProductsUseCase

  @Mock
  private lateinit var onlineChecker: OnlineChecker

  @Mock
  private lateinit var adapter: ProductListAdapter

  @Mock
  private lateinit var navigationService: NavigationService

  @Mock
  private lateinit var showLoadingObserver: Observer<Boolean>

  private val listOfProduct = arrayListOf<ProductEntity>()

  private val products = arrayListOf(ProductEntity(), ProductEntity(), ProductEntity())
  private val data = DataEntity(banner = "this is a banner", products = products)
  private val dataDomain = DataDomain(data)

  @Before
  fun setup() {
    Dispatchers.setMain(testDispatcher)

    viewModel = ProductListViewModel(
      getProductsUseCase,
      listOfProduct,
      adapter,
      onlineChecker,
      navigationService
    )
  }

  @Test
  fun `fetch product successfully`() = runTest {
    whenever(onlineChecker.isOnline()).thenReturn(true)
    whenever(getProductsUseCase()).thenReturn(Result.Success(dataDomain))

    viewModel.isShowDialogLoading.observeForever(showLoadingObserver)
    viewModel.start()

    verify(adapter, times(products.size)).notifyItemInserted(anyInt())
    verify(showLoadingObserver).onChanged(eq(false))
  }

  @Test
  fun `fetch product in offline state successfully`() = runTest {
    whenever(onlineChecker.isOnline()).thenReturn(false)
    whenever(getProductsUseCase()).thenReturn(Result.Success(dataDomain))

    viewModel.isShowDialogLoading.observeForever(showLoadingObserver)
    viewModel.start()

    verify(adapter, times(products.size)).notifyItemInserted(anyInt())
    verify(showLoadingObserver).onChanged(eq(false))
  }

  @Test
  fun `navigate to Product Details screen`() {
    val theFirstPosition = 0
    val navController: NavController = mock()

    val products = arrayListOf(ProductEntity(), ProductEntity(), ProductEntity())
    listOfProduct.addAll(products)

    viewModel.navigateFromListToDetails(theFirstPosition, navController)

    val product = products[theFirstPosition]
    verify(navigationService).navigateFromListToDetails(
      product.productName,
      "${product.price.toRupiahString()} / pcs",
      product.description,
      product.images?.large ?: "",
      navController
    )
  }

  @Test
  fun `refresh successfully`() = runTest {
    whenever(onlineChecker.isOnline()).thenReturn(true)
    whenever(getProductsUseCase()).thenReturn(Result.Success(dataDomain))

    viewModel.isShowDialogLoading.observeForever(showLoadingObserver)
    viewModel.refresh()

    verify(adapter, times(products.size)).notifyItemInserted(anyInt())
    verify(showLoadingObserver).onChanged(eq(false))
  }

  @Test
  fun `failed fetch product`() = runTest {
    val msg = "error in conversion"

    whenever(onlineChecker.isOnline()).thenReturn(true)
    whenever(getProductsUseCase()).thenReturn(Result.Error(msg))

    viewModel.isShowDialogLoading.observeForever(showLoadingObserver)
    viewModel.start()

    verify(navigationService).navigateFromListToError(msg)
    verify(showLoadingObserver).onChanged(eq(false))
  }
}
