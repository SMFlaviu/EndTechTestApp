package com.flaviu.endtechtestapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.flaviu.endtechtestapp.retrofit.ServiceBuilder
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.functions.BiFunction
import io.reactivex.rxjava3.schedulers.Schedulers

class ProductsViewModel : ViewModel() {

    val productsResult: MutableLiveData<ProductsResult> =
        MutableLiveData(ProductsResult(false, "empty request"))

    fun fetchProducts() {
        val firstResponse = ServiceBuilder.buildService().getFirstProducts()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
        val secondResponse = ServiceBuilder.buildService().getSecondProducts()
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
                    productsResult.value = ProductsResult(true, result)
                },
                { throwable ->
                    productsResult.value = ProductsResult(false, throwable.toString())
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