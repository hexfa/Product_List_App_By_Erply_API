package com.pim.data.model.image

data class Image(
    val SKU: String,
    val context: String,
    val createdTimestamp: Int,
    val description: String,
    val height: Int,
    val id: Int,
    val isDeleted: Boolean,
    val key: String,
    val order: Int,
    val productId: Int,
    val sequenceNr: Int,
    val size: Int,
    val tenant: String,
    val updatedTimestamp: Int,
    val width: Int
)