package com.example.homework_26.presentation.model

data class Item(
    val id: String,
    val name: String,
    val orderId: Int?,
    val children: List<Item>
)
