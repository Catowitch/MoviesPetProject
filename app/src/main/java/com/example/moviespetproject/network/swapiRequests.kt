package com.example.moviespetproject.network

import com.example.moviespetproject.model.CharacterProps
import com.example.moviespetproject.model.FilmResult
import retrofit2.http.GET
import retrofit2.http.Path

interface swapiRequests {
    @GET("films")
    suspend fun films(): FilmResult

    @GET("people/{id}")
    suspend fun character(@Path("id")id:String): SwBaseResponse<CharacterProps>
}