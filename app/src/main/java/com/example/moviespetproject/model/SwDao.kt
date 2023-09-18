package com.example.moviespetproject.model

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert


@Dao
interface SwDao{

    @Query("SELECT (SELECT COUNT(*) FROM film_res) == 0")
    fun isEmpty(): Boolean

    @Query("SELECT (SELECT COUNT(*) FROM characters) == 0")
    fun isCharactersEmpty(): Boolean

    @Upsert
    fun insertFilmsTest(filmResult: FilmResult)

    @Query("SELECT * FROM film_res")
    fun getFilmsFromDbTest(): FilmResult

    @Upsert
    fun upsertCharacter(character: SwCharacter)

    @Query("SELECT * FROM characters WHERE id=:id")
    fun getCharacter(id: Int?): SwCharacter

    @Query("SELECT * FROM characters")
    fun getCharacters(): List<SwCharacter>



}


