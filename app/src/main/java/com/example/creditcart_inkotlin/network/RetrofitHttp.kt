package com.example.creditcart_inkotlin.network

import com.example.creditcart_inkotlin.network.service.PhotosService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitHttp {
    private val IS_TESTER = true
    private val SERVER_DEVELOPMENT = "https://623303ad6de3467dbac5e484.mockapi.io/cards/"
    private val SERVER_PRODUCTION = "https://623303ad6de3467dbac5e484.mockapi.io/cards/"

    val retrofit = Retrofit.Builder().baseUrl(server()).addConverterFactory(GsonConverterFactory.create()).build()

    fun server(): String {
        if (IS_TESTER) return SERVER_DEVELOPMENT
        return SERVER_PRODUCTION
    }

    val posterService: PhotosService = retrofit.create(PhotosService::class.java)
    //...
}