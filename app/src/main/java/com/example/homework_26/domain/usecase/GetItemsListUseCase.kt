package com.example.homework_26.domain.usecase

import com.example.homework_26.domain.repository.ItemRepository
import javax.inject.Inject

class GetItemsListUseCase @Inject constructor(private val itemRepository: ItemRepository) {
    suspend operator fun invoke() = itemRepository.getItem()
}
