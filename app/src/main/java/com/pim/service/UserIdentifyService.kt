package com.pim.service

import com.pim.data.model.credential.Credentials
import io.reactivex.Single
import retrofit2.http.POST
import retrofit2.http.Url

interface UserIdentifyService {
    @POST
    fun login(@Url url:String): Single<Credentials>
}