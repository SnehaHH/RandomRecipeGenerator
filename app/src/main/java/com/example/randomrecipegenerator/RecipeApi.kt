package com.example.randomrecipegenerator

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET

private val retrofit =
    Retrofit.Builder().baseUrl("https://www.themealdb.com/api/json/v1/1/").addConverterFactory(GsonConverterFactory.create())
        .build()

val reciperService = retrofit.create(RecipeApi::class.java)

interface RecipeApi {
    @GET("random.php")
    suspend fun getRecipe(): RecipeDataResponse
}

