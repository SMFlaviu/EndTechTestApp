package com.flaviu.endtechtestapp

class ProductsResult {
    var isSuccess: Boolean
        private set
    var error: String? = null
        private set

    var products: List<ProductDetails>? = null

    constructor(isSuccess: Boolean, products: List<ProductDetails>) {
        this.isSuccess = isSuccess
        this.products = products
    }

    constructor(isSuccess: Boolean, error: String?) {
        this.isSuccess = isSuccess
        this.error = error
    }
}
