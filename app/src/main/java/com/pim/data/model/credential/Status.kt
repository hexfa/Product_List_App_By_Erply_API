package com.pim.data.model.credential

data class Status(
    val errorCode: Int,
    val generationTime: Double,
    val recordsInResponse: Int,
    val recordsTotal: Int,
    val request: String,
    val requestUnixTime: Int,
    val responseStatus: String
)