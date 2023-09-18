package com.example.moviespetproject.model

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(
    entities = [FilmResult::class, SwCharacter::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(com.example.moviespetproject.model.TypeConverters::class)
abstract class FilmDatabase: RoomDatabase() {
    abstract val dao: SwDao
}