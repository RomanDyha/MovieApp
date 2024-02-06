package com.themoviedb.org.data.data_source.remote

import com.themoviedb.org.data.models.MoviesResponse
import retrofit2.Response
// all requests to the backend
interface MoviesRemoteDataSource {

  suspend fun searchMovies(query: String): Response<MoviesResponse>

}