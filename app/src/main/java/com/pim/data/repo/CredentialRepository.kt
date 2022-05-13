package com.pim.data.repo

import com.pim.data.model.credential.Credentials
import com.pim.service.UserIdentifyService
import io.reactivex.Single
import javax.inject.Inject

class CredentialRepository @Inject constructor(
    private val userIdentifyService: UserIdentifyService,
) {
    fun login(username: String?, password: String?, clientCode: String?): Single<Credentials> {
        return userIdentifyService.login(
            "https://${clientCode}.erply.com/api/?clientCode=${clientCode}&username=${username}&password=${password}&request=verifyUser&sessionLength=86400"
        )
    }

}