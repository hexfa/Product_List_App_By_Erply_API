package com.pim.serviece

import com.pim.data.model.product_list.ProductList
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ProductService {

    @GET("product")
    fun getProductList(
        @Header("jwt") token: String,
        @Query("skip") skip: String,
        @Query("take") take: String
    ): Single<ProductList>

    @GET("product")
    fun getProductListSearch(
        @Query("filter") filter: String,
        @Header("jwt") token: String,
        @Query("skip") skip: String,
        @Query("take") take: String
    ): Single<ProductList>
}