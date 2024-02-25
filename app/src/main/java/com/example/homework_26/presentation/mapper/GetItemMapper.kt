package com.example.homework_26.presentation.mapper

import com.example.homework_26.domain.model.GetItem
import com.example.homework_26.presentation.model.Item

fun GetItem.toPresenter(): Item {
    return Item(
        id = id,
        name = name,
        orderId = orderId,
        children = children.map { it.toPresenter() }
    )
}
