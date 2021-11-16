package com.flaviu.endtechtestapp.retrofit

import com.flaviu.endtechtestapp.data.ProductDetails

data class ProductResponse (
    val products : List<ProductDetails>,
    val title: String,
    val product_count: Int)