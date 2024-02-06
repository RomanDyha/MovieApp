package com.themoviedb.org.data.data_source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MoviesDao {
    // save movies to database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(moviesList: List<MovieEntity>)

    // get movie details by id
    @Query("SELECT * FROM movie WHERE id= :movieId")
    fun getMovieById(movieId: Int): MovieEntity

}