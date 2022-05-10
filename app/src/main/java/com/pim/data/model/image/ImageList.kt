package com.pim.data.model.image

data class ImageList(
    val images: List<Image>,
    val page: Int,
    val recordsPerPage: Int,
    val recordsReturned: Int,
    val totalRecords: Int
)