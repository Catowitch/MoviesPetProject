package com.example.moviespetproject.network

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.moviespetproject.model.FilmProps
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class SwBaseResponse<T>(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val message: String,
    @TypeConverters(com.example.moviespetproject.model.TypeConverters::class)
    val result: SwBaseResponseResult<T>
)


@JsonClass(generateAdapter = true)
data class SwBaseResponseResult<T>(
    @TypeConverters(com.example.moviespetproject.model.TypeConverters::class)
    @Json(name = "properties") val properties: T,
    val description: String,
    @Json(name = "_id") val id: String,
    val uid: String,
    @Json(name = "__v") val v: Int
)



@JsonClass(generateAdapter = true)
data class SwBaseResponseMultiple<T>(
    val message: String,
    @TypeConverters(com.example.moviespetproject.model.TypeConverters::class)
    val result: List<SwBaseResponseResult<T>>
)


// For multi page requests
@JsonClass(generateAdapter = true)
data class PagerSwBaseResponse<T>(
    val message: String,
    val total_records: Int,
    val total_pages:Int,
    val previous: String?,
    val next: String?,
    @TypeConverters(com.example.moviespetproject.model.TypeConverters::class)
    val results: List<SwBaseResponseResult<T>>
)

// Result for multipage Data
@JsonClass(generateAdapter = true)
data class PageResult(
    val uid: String,
    val name: String,
    val url: String
)