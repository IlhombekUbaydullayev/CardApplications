package com.example.creditcart_inkotlin.network.service

import com.example.creditcart_inkotlin.model.Card
import retrofit2.Call
import retrofit2.http.*


interface PhotosService {

    @Headers(
        "Content-type:application/json"
    )

    @GET("cards")
    fun listPost(): Call<ArrayList<Card>>

    @GET("cards/{id}")
    fun singlePost(@Path("id") id: Int): Call<Card>

    @POST("cards")
    fun createPost(@Body post: Card): Call<Card>

    @PUT("cards/{id}")
    fun updatePost(@Path("id") id: Int, @Body post: Card): Call<Card>

    @DELETE("cards/{id}")
    fun deletePost(@Path("id") id: Int): Call<Card>

}