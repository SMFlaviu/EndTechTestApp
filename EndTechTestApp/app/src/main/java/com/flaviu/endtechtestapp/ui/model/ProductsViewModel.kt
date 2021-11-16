package com.flaviu.endtechtestapp.ui.model

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.flaviu.endtechtestapp.data.ProductDetails
import com.flaviu.endtechtestapp.retrofit.APIRequest
import com.flaviu.endtechtestapp.retrofit.ProductResponse
import com.flaviu.endtechtestapp.util.DataState
import com.flaviu.endtechtestapp.util.DataStateEvent
import com.flaviu.endtechtestapp.util.EspressoIdlingResource
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.functions.BiFunction
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject


@HiltViewModel
class ProductsViewModel
@Inject
constructor(
    private val provideClothsService: APIRequest
) : ViewModel() {

    private val _productsState: MutableLiveData<DataState<List<ProductDetails>>> = MutableLiveData()
    val productsState: LiveData<DataState<List<ProductDetails>>>
        get() = _productsState

    fun setDataStateEvent(stateEvent: DataStateEvent) {
        when (stateEvent) {
            is DataStateEvent.GetClothesEvent -> {
                _productsState.value = DataState.Loading
                fetchProducts()
            }
        }
    }

    fun fetchProducts() {
        val firstResponse = provideClothsService.getFirstProducts()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
        val secondResponse = provideClothsService.getSecondProducts()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

        Observable.zip(
            firstResponse,
            secondResponse,
            BiFunction<ProductResponse, ProductResponse, List<ProductDetails>> { firstProduct, secondProduct ->
                return@BiFunction combineProducts(firstProduct, secondProduct)
            })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    _productsState.value = DataState.Success(result)
                },
                { throwable ->
                    _productsState.value = DataState.Error(throwable as Exception)
                }
            )
    }

    private fun combineProducts(
        firstProducts: ProductResponse,
        secondProducts: ProductResponse
    ): List<ProductDetails> {
        // .toSet()
        // adding the above will result only in 2 items
        return firstProducts.products
            .plus(secondProducts.products).sortedByDescending { it.id }
    }
}