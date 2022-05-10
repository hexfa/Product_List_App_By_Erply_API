package com.pim.data.model.credential

data class Record(
    val berlinPOSAssetsURL: String,
    val berlinPOSVersion: String,
    val couponRegistryURLs: List<Any>,
    val customerRegistryURLs: List<Any>,
    val displayAdManagerURLs: List<Any>,
    val employeeID: String,
    val employeeName: String,
    val epsiDownloadURLs: List<EpsiDownloadURL>,
    val epsiURL: String,
    val groupID: String,
    val groupName: String,
    val identityToken: String,
    val ipAddress: String,
    val isPasswordExpired: Boolean,
    val loginUrl: String,
    val remindUserToUpdateUsername: Int,
    val sessionKey: String,
    val sessionLength: Int,
    val token: String,
    val userID: String,
    val userName: String
)