package com.themoviedb.org.data.data_source.local

import androidx.room.Database
import androidx.room.RoomDatabase
// Create movies database
@Database(entities = [MovieEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun moviesDao(): MoviesDao

}