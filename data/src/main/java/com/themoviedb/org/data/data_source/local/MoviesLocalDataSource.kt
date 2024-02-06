package com.themoviedb.org.data.data_source.local

// all database queries
interface MoviesLocalDataSource {

    fun getMovieById(movieId: Int): MovieEntity

    suspend fun saveMovies(moviesList: List<MovieEntity>)

}