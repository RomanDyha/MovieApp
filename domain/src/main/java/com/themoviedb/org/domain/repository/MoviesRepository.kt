package com.themoviedb.org.domain.repository

import com.themoviedb.org.domain.models.DataStatus
import com.themoviedb.org.domain.models.MovieDomain
import kotlinx.coroutines.flow.Flow

// all methods for working with data sources
interface MoviesRepository {

    suspend fun searchMovies(query: String): Flow<DataStatus<List<MovieDomain>>>

    fun getMovieById(movieId: Int): Flow<DataStatus<MovieDomain>>

}