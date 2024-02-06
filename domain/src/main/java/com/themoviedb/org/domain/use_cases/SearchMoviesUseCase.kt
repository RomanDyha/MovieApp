package com.themoviedb.org.domain.use_cases

import com.themoviedb.org.domain.models.DataStatus
import com.themoviedb.org.domain.models.MovieDomain
import com.themoviedb.org.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow

// use case to find movies
class SearchMoviesUseCase(val moviesRepository: MoviesRepository) {

    private var characterCount = 0

    suspend fun invoke(query: String): Flow<DataStatus<List<MovieDomain>>> =
        moviesRepository.searchMovies(query)

    fun isInputDataCorrectly(): Boolean {
        characterCount++
        if (characterCount == 2) {
            characterCount = 0
            return true
        }
        return false
    }

}