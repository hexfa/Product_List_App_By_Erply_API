package com.pim.data.model.product_list

data class Product(
    val added: Int,
    val addedby: String,
    val age_restriction: Int,
    val alcohol_percentage: String,
    val alcohol_registry_number: String,
    val batches: String,
    val brand_id: Int,
    val cashier_must_enter_price: Int,
    val category_id: Int,
    val changed: Int,
    val changedby: String,
    val code: String,
    val code2: String,
    val code3: String,
    val code5: String,
    val code6: String,
    val code7: String,
    val code8: String,
    val cost: Int,
    val country_of_origin_id: Int,
    val delivery_time: String,
    val deposit_fee_amount: Int,
    val deposit_fee_id: Int,
    val description: Description,
    val displayed_in_webshop: Int,
    val excise_declaration_number: String,
    val extra_field1_id: Int,
    val extra_field2_id: Int,
    val extra_field3_id: Int,
    val extra_field4_id: Int,
    val family_id: Int,
    val gross_weight: Int,
    val group_id: Int,
    val has_serial_numbers: Int,
    val height: Int,
    val id: Int,
    val is_gift_card: Int,
    val is_regular_gift_card: Int,
    val labels_not_needed: Int,
    val length: Int,
    val location_in_warehouse_id: Int,
    val location_in_warehouse_text: String,
    val manufacturer_name: String,
    val name: Name,
    val net_weight: Int,
    val non_discountable: Int,
    val non_refundable: Int,
    val non_stock_product: Int,
    val packaging_type: String,
    val packing_not_required: Int,
    val parent_product_id: Int,
    val price: Double,
    val price_with_tax: Int,
    val priority_group_id: Int,
    val product_reorder_multiples: Int,
    val reward_points_not_allowed: Int,
    val sold_in_packages: Int,
    val status: String,
    val suggested_retail_price: Int,
    val supplier_code: String,
    val supplier_id: Int,
    val tax_free: Int,
    val tax_rate_id: Int,
    val type: String,
    val unit_id: Int,
    val volume: Int,
    val width: Int
)