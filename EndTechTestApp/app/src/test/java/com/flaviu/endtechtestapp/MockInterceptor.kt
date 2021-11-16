package com.flaviu.endtechtestapp

import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody

class MockInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val uri = chain.request().url.toUri().toString()
        val responseString = when {
            uri.endsWith("media/catalog/android_test_example.json") -> getListOfClothesFromFirstDataSource
            uri.endsWith("media/catalog/example.json") -> getListOfClothesFromSecondDataSource
            else -> ""
        }

        return chain.proceed(chain.request())
            .newBuilder()
            .code(200)
            .protocol(Protocol.HTTP_2)
            .message(responseString)
            .body(
                responseString.toByteArray()
                    .toResponseBody("application/json".toMediaTypeOrNull())
            )
            .addHeader("content-type", "application/json")
            .build()
    }

    private val getListOfClothesFromFirstDataSource = """
    {
        "products": [
            {
              "id": "1",
              "name": "Test Shirt",
              "price": "£199",
              "image": "https://media.endclothing.com/media/f_auto,q_auto,w_760,h_760/prodmedia/media/catalog/product/2/6/26-03-2018_gosha_rubchinskiyxadidas_copaprimeknitboostmidsneaker_yellow_g012sh12-220_ka_1.jpg"
            },
            {
              "id": "2",
              "name": "Test Shirt",
              "price": "£199",
              "image": "https://media.endclothing.com/media/f_auto,q_auto:eco/prodmedia/media/catalog/product/2/2/22-02-2021_MO_HB8059_1_1.jpg"
            }
        ],
        "title": "Exercise Listing",
        "product_count": 2
    }
    """

    private val getListOfClothesFromSecondDataSource = """
    {
        "products": [
            {
              "id": "1",
              "name": "Test Shirt",
              "price": "£199",
              "image": "https://media.endclothing.com/media/f_auto,q_auto,w_760,h_760/prodmedia/media/catalog/product/2/6/26-03-2018_gosha_rubchinskiyxadidas_copaprimeknitboostmidsneaker_yellow_g012sh12-220_ka_1.jpg"
            }
        ],
        "title": "Exercise Listing",
        "product_count": 1
    }
    """
}