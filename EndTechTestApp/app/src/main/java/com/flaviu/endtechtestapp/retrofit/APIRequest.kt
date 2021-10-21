package com.flaviu.endtechtestapp.retrofit

import com.flaviu.endtechtestapp.ProductResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET

interface APIRequest {
    @GET("media/catalog/android_test_example.json")
    fun getFirstProducts(): Observable<ProductResponse>

    @GET("media/catalog/example.json")
    fun getSecondProducts(): Observable<ProductResponse>
}