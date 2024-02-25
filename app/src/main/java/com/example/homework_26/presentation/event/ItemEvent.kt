package com.example.homework_26.presentation.event

sealed class ItemEvent {
    object FetchItems : ItemEvent()
    object ResetErrorMessage: ItemEvent()
    data class Filter(var title: String? = null): ItemEvent()
}
