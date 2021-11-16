package com.flaviu.endtechtestapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.flaviu.endtechtestapp.di.NetworkModule
import com.flaviu.endtechtestapp.retrofit.APIRequest
import com.flaviu.endtechtestapp.ui.model.ProductsViewModel
import com.flaviu.endtechtestapp.util.DataState
import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.OkHttpClient
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class ProductsViewModelTest {

    private lateinit var networkApi: APIRequest
    private lateinit var productsViewModel: ProductsViewModel

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        networkApi = NetworkModule
            .provideClothsService(
                OkHttpClient
                    .Builder()
                    .addInterceptor(MockInterceptor())
                    .build()
            )

        productsViewModel = ProductsViewModel(networkApi)

        // To mock RxJava multi-threading
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setNewThreadSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
    }

    @Test
    fun `Testing ViewModel functionality`() {
        productsViewModel.fetchProducts()

        // Required by rxJava to complete it's calls
        Thread.sleep(5000)

        val productDetailsList = productsViewModel.productsState.value

        // Testing if items arrived
        assertNotNull(productDetailsList)

        // Testing if state is Success
        assertTrue(productDetailsList is DataState.Success)

        // Testing if the number of items is correct
        assertEquals((productDetailsList as DataState.Success).data.size, 3)

        // Testing if the order is correct
        assertEquals((productDetailsList as DataState.Success).data[0].id, 2)
    }
}