package com.themoviedb.org.data.data_source.remote

import com.themoviedb.org.data.models.MoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

// ApiService interface for retrofit 2
interface MoviesApiService {

    //https://api.themoviedb.org/3/search/movie?query=Jack+Reacher&api_key=API_KEY'
    @GET("search/movie")
    suspend fun searchMovie(
        @Query("query") query: String,
        @Query("api_key") api_key: String,
        @Query("language") language: String,
    ): Response<MoviesResponse>

}

