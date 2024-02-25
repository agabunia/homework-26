package com.example.homework_26.data.repository

import com.example.homework_26.data.common.HandleResponse
import com.example.homework_26.data.common.Resource
import com.example.homework_26.data.mapper.asResource
import com.example.homework_26.data.mapper.toDomain
import com.example.homework_26.data.service.ItemService
import com.example.homework_26.domain.model.GetItem
import com.example.homework_26.domain.repository.ItemRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ItemRepositoryImpl @Inject constructor(
    private val handleResponse: HandleResponse,
    private val itemService: ItemService
) : ItemRepository {
    override suspend fun getItem(): Flow<Resource<List<GetItem>>> {
        return handleResponse.safeApiCall {
            itemService.getItem()
        }.asResource { list ->
            list.map {
                it.toDomain()
            }

        }
    }
}
