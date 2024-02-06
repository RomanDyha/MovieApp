package com.themoviedb.org.data.repository

import com.themoviedb.org.data.data_source.local.MoviesLocalDataSource
import com.themoviedb.org.data.data_source.local.toDomainModel
import com.themoviedb.org.data.data_source.remote.MoviesRemoteDataSource
import com.themoviedb.org.data.models.MoviesResponse
import com.themoviedb.org.data.models.toDomainModel
import com.themoviedb.org.data.models.toEntityModel
import com.themoviedb.org.domain.models.DataStatus
import com.themoviedb.org.domain.repository.MoviesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

// pattern Repository used to abstract the origin of data, whether it's coming from a local database, a network request, or any other source.
class MoviesRepositoryImpl(val moviesRemoteDataSource: MoviesRemoteDataSource,
                           val moviesLocalDataSource: MoviesLocalDataSource
) : MoviesRepository {

    // query for searching movies on the backend
    override suspend fun searchMovies(query: String) = flow {
        emit(DataStatus.loading())
        val result = moviesRemoteDataSource.searchMovies(query)
        if(responseIsSuccessful(result)) {
            val moviesList = DataStatus.success(data = result.body()!!.results.take(10).toDomainModel())

            // save received films to the database
            moviesLocalDataSource.saveMovies(
                moviesList.data!!.toEntityModel()
            )

            emit(moviesList)
        }else {
            emit(DataStatus.error(result.message()))
        }
    }
        .catch {
            emit(DataStatus.error(it.message.toString()))
        }
        .flowOn(Dispatchers.IO)

    // get movie details from the database
    override fun getMovieById(movieId: Int) = flow {
        val result = moviesLocalDataSource.getMovieById(movieId)
        if(result != null){
            val movieDetails = DataStatus.success(data = result.toDomainModel())
            emit(movieDetails)
        }
    }.catch {
        emit(DataStatus.error(it.message.toString()))
    }
        .flowOn(Dispatchers.IO)

    // check response
    private fun responseIsSuccessful(result: Response<MoviesResponse>) : Boolean {
        if(result.isSuccessful && result.body() != null
            && !result.body()!!.results.isNullOrEmpty()) {
            return true
        }
        return false
    }

}



