package com.example.homework_26.presentation.model

data class FlattenedItems(
    val item: Item,
    val deepestLevel: Int,
    val parentCount: Int
)