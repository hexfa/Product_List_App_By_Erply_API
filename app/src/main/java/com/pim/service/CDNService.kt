package com.pim.service

import com.pim.data.model.image.ImageList
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface CDNService {
    @GET("images")
    fun getImages(
        @Header("jwt") token: String,
        @Query("productId") productId: String
    ): Single<ImageList>
}