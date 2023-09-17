package com.example.moviespetproject.network

import com.example.moviespetproject.model.CharacterProps
import com.example.moviespetproject.model.FilmResult
import com.example.moviespetproject.model.SwCharacter
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface swapiRequests {
    @GET("films")
    suspend fun films(): FilmResult

    @GET("people/{id}")
    suspend fun character(@Path("id")id:String): SwBaseResponse<CharacterProps>

    /*@GET
    suspend fun characterFullUrl(@Url url: String): SwBaseResponseResult<SwCharacter>*/
}