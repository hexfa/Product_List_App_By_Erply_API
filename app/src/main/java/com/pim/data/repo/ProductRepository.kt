package com.pim.data.repo

import com.pim.data.model.product_list.ProductList
import com.pim.serviece.ProductService
import io.reactivex.Single
import javax.inject.Inject

class ProductRepository @Inject constructor(private val productService: ProductService) {

    fun getProductList(token: String, skip: String, take: String): Single<ProductList> =
        productService.getProductList(token, skip, take)

    fun getProductListSearch(
        filter: String,
        token: String,
        skip: String,
        take: String
    ): Single<ProductList> = productService.getProductListSearch(filter, token, skip, take)

}