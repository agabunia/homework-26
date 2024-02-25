package com.example.homework_26.domain.usecase

import com.example.homework_26.data.common.Resource
import com.example.homework_26.domain.model.GetItem
import com.example.homework_26.domain.repository.ItemRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FilterItemsUseCase @Inject constructor(private val repository: ItemRepository) {
    suspend operator fun invoke(search: String?): Flow<Resource<List<GetItem>>> {
        return repository.getItem().map { resource ->
            when (resource) {
                is Resource.Success -> {
                    if (search.isNullOrBlank()) {
                        Resource.Success(resource.data)
                    } else {
                        val filteredItems = filterItemsAndNestedItems(resource.data, search)
                        Resource.Success(filteredItems)
                    }
                }

                is Resource.Error -> resource
                is Resource.Loading -> resource
            }
        }
    }

    private fun filterItemsAndNestedItems(items: List<GetItem>, search: String): List<GetItem> {
        val filteredItems = mutableListOf<GetItem>()

        for (item in items) {
            if (item.name.contains(search, ignoreCase = true)) {
                filteredItems.add(item)
            }
            val filteredNestedItems = filterItemsAndNestedItems(item.children, search)
            filteredItems.addAll(filteredNestedItems)
        }

        return filteredItems
    }
}