package com.example.moviespetproject.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.moviespetproject.network.SwBaseResponseResult
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@Entity(tableName = "film_res")
@JsonClass(generateAdapter = true)
data class FilmResult(
    @PrimaryKey
    val id: Int? = null,
    val message: String,
    @TypeConverters(com.example.moviespetproject.model.TypeConverters::class)
    var result: List<SwBaseResponseResult<FilmProps>>
)


@JsonClass(generateAdapter = true)
data class FilmProps(
    @Json(name = "characters") val charactersURLs: List<String>,
    @Json(name = "planets") val planetsURLs: List<String>,
    @Json(name = "starships") val starshipsURLs: List<String>,
    @Json(name = "vehicles") val vehiclesURLs: List<String>,
    @Json(name = "species") val speciesURLs: List<String>,
    @Json(name = "episode_id") val episodeNumber: String,
    @Json(name = "opening_crawl") val openingCrawl: String,
    @Json(name = "release_date") val releaseDate: String,
    @Json(name = "producer") val producers: String,
    val title: String,
    val director: String,
    val created: String,
    val edited: String,
    val url: String
)

