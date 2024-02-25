package com.example.homework_26.domain.model

data class GetItem(
    val id: String,
    val name: String,
    val orderId: Int?,
    val children: List<GetItem>
)
