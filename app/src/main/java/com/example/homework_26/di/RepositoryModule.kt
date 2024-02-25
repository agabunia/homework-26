package com.example.homework_26.di

import com.example.homework_26.data.common.HandleResponse
import com.example.homework_26.data.repository.ItemRepositoryImpl
import com.example.homework_26.data.service.ItemService
import com.example.homework_26.domain.repository.ItemRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideItemRepository(
        handleResponse: HandleResponse,
        itemService: ItemService
    ): ItemRepository {
        return ItemRepositoryImpl(handleResponse = handleResponse, itemService = itemService)
    }

}