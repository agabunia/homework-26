package com.example.homework_26.domain.repository

import com.example.homework_26.data.common.Resource
import com.example.homework_26.domain.model.GetItem
import kotlinx.coroutines.flow.Flow

interface ItemRepository {
    suspend fun getItem(): Flow<Resource<List<GetItem>>>
}
