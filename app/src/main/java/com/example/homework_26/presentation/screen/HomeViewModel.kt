package com.example.homework_26.presentation.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework_26.data.common.Resource
import com.example.homework_26.domain.usecase.FilterItemsUseCase
import com.example.homework_26.domain.usecase.GetItemsListUseCase
import com.example.homework_26.presentation.event.ItemEvent
import com.example.homework_26.presentation.mapper.toPresenter
import com.example.homework_26.presentation.model.FlattenedItems
import com.example.homework_26.presentation.model.Item
import com.example.homework_26.presentation.state.ItemState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getItemsListUseCase: GetItemsListUseCase,
    private val filterItemsUseCase: FilterItemsUseCase
) : ViewModel() {

    private val _itemState = MutableStateFlow((ItemState()))
    val itemState: SharedFlow<ItemState> = _itemState.asStateFlow()

    fun onEvent(event: ItemEvent) {
        when (event) {
            is ItemEvent.FetchItems -> fetchItems()
            is ItemEvent.ResetErrorMessage -> errorMessage(message = null)
            is ItemEvent.Filter -> searchTitle(search = event.title)
        }
    }

    private fun fetchItems() {
        viewModelScope.launch {
            getItemsListUseCase().collect {
                when (it) {
                    is Resource.Success -> {
                        _itemState.update { currentState ->
                            currentState.copy(itemList = flattenedItem(
                                it.data.map { list ->
                                    list.toPresenter()
                                }
                            ))
                        }
                    }

                    is Resource.Error -> {
                        errorMessage(message = it.errorMessage)
                    }

                    is Resource.Loading -> {
                        _itemState.update { currentState -> currentState.copy(isLoading = it.loading) }
                    }
                }
            }
        }
    }

    private fun searchTitle(search: String?) {
        viewModelScope.launch {
            filterItemsUseCase(search = search).collect {
                when (it) {
                    is Resource.Success -> {
                        _itemState.update { currentState ->
                            currentState.copy(itemList = flattenedItem(
                                it.data.map { list ->
                                    list.toPresenter()
                                }
                            ))
                        }
                    }

                    is Resource.Error -> {
                        errorMessage(message = it.errorMessage)
                    }

                    is Resource.Loading -> {
                        _itemState.update { currentState -> currentState.copy(isLoading = it.loading) }
                    }
                }
            }
        }
    }

    private fun errorMessage(message: String?) {
        _itemState.update { currentState -> currentState.copy(errorMessage = message) }
    }

    private fun flattenedItem(items: List<Item>, parentCount: Int = 0): List<FlattenedItems> {
        val flattenedList = mutableListOf<FlattenedItems>()
        for (item in items) {
            val deepestLevel = findDeepestLevel(item)
            flattenedList.add(FlattenedItems(item, deepestLevel, parentCount))
            flattenedList.addAll(flattenedItem(item.children, parentCount + 1))
        }
        return flattenedList
    }

    private fun findDeepestLevel(item: Item): Int {
        if (item.children.isEmpty()) {
            return 0
        }
        val childLevels = item.children.map { findDeepestLevel(it) }
        return childLevels.maxOrNull()!! + 1
    }

}
