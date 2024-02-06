package com.themoviedb.org.data.data_source.remote

import com.themoviedb.org.data.BuildConfig
import com.themoviedb.org.data.models.MoviesResponse
import retrofit2.Response
import java.util.Locale

// implementation RemoteDataSource
class MoviesRemoteDataSourceImpl(private val moviesApiService: MoviesApiService): MoviesRemoteDataSource {

    override suspend fun searchMovies(query: String): Response<MoviesResponse> {
        // send query, api_key(themoviedb), locale
        return moviesApiService.searchMovie(query, BuildConfig.API_KEY,
            Locale.getDefault().toLanguageTag() )
    }

}