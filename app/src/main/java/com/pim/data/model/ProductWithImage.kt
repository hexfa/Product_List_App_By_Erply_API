package com.pim.data.model

import com.pim.data.model.product_list.Name

data class ProductWithImage(
    val name: Name,
    val id: Int,
    val price: Double,
    val cost: Int,
    val url: String?
)