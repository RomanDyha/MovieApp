package com.themoviedb.org.data.di

import com.themoviedb.org.data.BuildConfig
import com.themoviedb.org.data.data_source.remote.MoviesApiService
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// DI retrofit 2
val networkModule = module {

    single {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().build())
            .build()
    }

    single { get<Retrofit>().create(MoviesApiService::class.java) }

}