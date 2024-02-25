package com.example.homework_26.presentation.state

import com.example.homework_26.presentation.model.FlattenedItems
import com.example.homework_26.presentation.model.Item

data class ItemState(
    val itemList: List<FlattenedItems>? = null,
    val errorMessage: String? = null,
    val isLoading: Boolean = false
)
