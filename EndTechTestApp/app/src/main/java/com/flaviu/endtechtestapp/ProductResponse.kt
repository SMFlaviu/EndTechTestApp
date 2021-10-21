package com.flaviu.endtechtestapp

data class ProductResponse (
    val products : List<ProductDetails>,
    val title: String,
    val product_count: Int)