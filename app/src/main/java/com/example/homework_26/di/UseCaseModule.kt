package com.example.homework_26.di

import com.example.homework_26.domain.repository.ItemRepository
import com.example.homework_26.domain.usecase.FilterItemsUseCase
import com.example.homework_26.domain.usecase.GetItemsListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Singleton
    @Provides
    fun provideGetItemsListUseCase(
        repository: ItemRepository
    ): GetItemsListUseCase {
        return GetItemsListUseCase(itemRepository = repository)
    }

    @Singleton
    @Provides
    fun provideFilterItemsListUseCase(
        repository: ItemRepository
    ): FilterItemsUseCase {
        return FilterItemsUseCase(repository = repository)
    }
}