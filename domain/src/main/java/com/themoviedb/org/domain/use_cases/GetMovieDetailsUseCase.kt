package com.themoviedb.org.domain.use_cases

import com.themoviedb.org.domain.models.DataStatus
import com.themoviedb.org.domain.models.MovieDomain
import com.themoviedb.org.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow

// use case to find movie details
class GetMovieDetailsUseCase(val moviesRepository: MoviesRepository) {

    fun invoke(movieId: Int): Flow<DataStatus<MovieDomain>> =
         moviesRepository.getMovieById(movieId = movieId)

}