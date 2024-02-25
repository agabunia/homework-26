package com.example.homework_26.data.mapper

import com.example.homework_26.data.model.ItemDto
import com.example.homework_26.domain.model.GetItem

fun ItemDto.toDomain(): GetItem {
    return GetItem(
        id = id,
        name = name,
        orderId = orderId,
        children = children.map { it.toDomain() }
    )
}
