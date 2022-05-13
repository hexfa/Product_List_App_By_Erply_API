package com.pim.data.repo

import com.pim.data.model.image.ImageList
import com.pim.service.CDNService
import io.reactivex.Single
import javax.inject.Inject

class ImageRepository @Inject constructor(private val cdnService: CDNService) {
    fun getImage(token: String, productIDs: String): Single<ImageList> =
        cdnService.getImages(token, productIDs)
}