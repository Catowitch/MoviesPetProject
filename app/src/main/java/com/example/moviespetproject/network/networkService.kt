package com.example.moviespetproject.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory



internal val moshi by lazy {
    Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
        //.add(Iso8601DateTimeTypeAdapter())
}


val BASE_URL = "https://www.swapi.tech/api/"



private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()



object starWarsApi{
    val retrofitService: swapiRequests by lazy{
        retrofit.create(swapiRequests::class.java)
    }
}
