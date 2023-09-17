package com.example.moviespetproject.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.moviespetproject.network.SwBaseResponseResult
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Entity(tableName = "characters")
@JsonClass(generateAdapter = true)
data class SwCharacter(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val message: String,
    @TypeConverters(com.example.moviespetproject.model.TypeConverters::class)
    val result: SwBaseResponseResult<CharacterProps>
)



@JsonClass(generateAdapter = true)
data class CharacterProps(
    @Json(name="hair_color")val hairColor: String,
    @Json(name="skin_color")val skinColor: String,
    @Json(name="eye_color")val eyeColor: String,
    @Json(name="birth_year")val birthYear: String,
    val height: String,
    val mass: String,
    val gender: String,
    val created: String,
    val edited: String,
    val name: String,
    val homeworld: String,
    val url: String
)
