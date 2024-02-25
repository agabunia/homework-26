package com.example.homework_26.data.service

import com.example.homework_26.data.model.ItemDto
import retrofit2.Response
import retrofit2.http.GET

interface ItemService {
    @GET("https://run.mocky.io/v3/6f722f19-3297-4edd-a249-fe765e8104db?search=title")
    suspend fun getItem(): Response<List<ItemDto>>
}