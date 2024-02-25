package com.example.homework_26.data.model

import com.squareup.moshi.Json

data class ItemDto(
    @Json(name = "id")
    val id: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "order_id")
    val orderId: Int?,
    @Json(name = "children")
    val children: List<ItemDto>
)
