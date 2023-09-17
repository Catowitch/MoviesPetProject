package com.example.moviespetproject.model

import androidx.room.TypeConverter
import com.example.moviespetproject.network.SwBaseResponse
import com.example.moviespetproject.network.SwBaseResponseResult
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class TypeConverters {
    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    private val listStringAdapter: JsonAdapter<List<String>> =
        moshi.adapter(Types.newParameterizedType(List::class.java, String::class.java))


    private val filmAdapter: JsonAdapter<FilmResult> =
        moshi.adapter(FilmResult::class.java)

    private val swBaseResponseCharacterAdapter: JsonAdapter<SwBaseResponse<CharacterProps>> =
        moshi.adapter(Types.newParameterizedType(SwBaseResponse::class.java, CharacterProps::class.java))

    private val swBaseResponseResultCharacterAdapter: JsonAdapter<SwBaseResponseResult<CharacterProps>> =
        moshi.adapter(Types.newParameterizedType(SwBaseResponseResult::class.java, CharacterProps::class.java))

    private val swBaseResponseFilmAdapter: JsonAdapter<SwBaseResponse<CharacterProps>> =
        moshi.adapter(Types.newParameterizedType(SwBaseResponse::class.java, CharacterProps::class.java))


    private val swBaseResponseResultListStringAdapter: JsonAdapter<List<SwBaseResponseResult<FilmProps>>> =
        moshi.adapter(Types.newParameterizedType(List::class.java, Types.newParameterizedType(SwBaseResponseResult::class.java,FilmProps::class.java)))




    @TypeConverter
    fun fromStringList(value: String?): List<String>? {
        return value?.let { listStringAdapter.fromJson(it) }
    }

    @TypeConverter
    fun toStringList(value: List<String>?): String? {
        return listStringAdapter.toJson(value)
    }

    @TypeConverter
    fun toFilms(value: String?): FilmResult?{
        return value?.let{ filmAdapter.fromJson(it) }
    }

    @TypeConverter
    fun fromFilms(value: FilmResult?): String? {
        return filmAdapter.toJson(value)
    }

    @TypeConverter
    fun fromSwBaseList(value: String?): List<SwBaseResponseResult<FilmProps>>? {
        return value?.let { swBaseResponseResultListStringAdapter.fromJson(it) }
    }

    @TypeConverter
    fun toSwBaseList(value: List<SwBaseResponseResult<FilmProps>>?): String? {
        return swBaseResponseResultListStringAdapter.toJson(value)
    }


    @TypeConverter
    fun fromCharacter(value: String?): SwBaseResponse<CharacterProps>? {
        return value?.let { swBaseResponseCharacterAdapter.fromJson(it) }
    }

    @TypeConverter
    fun toCharacter(value: SwBaseResponse<CharacterProps>?): String? {
        return swBaseResponseCharacterAdapter.toJson(value)
    }


    @TypeConverter
    fun fromCharacterResult(value: String?): SwBaseResponseResult<CharacterProps>? {
        return value?.let { swBaseResponseResultCharacterAdapter.fromJson(it) }
    }

    @TypeConverter
    fun toCharacterResult(value: SwBaseResponseResult<CharacterProps>?): String? {
        return swBaseResponseResultCharacterAdapter.toJson(value)
    }
}