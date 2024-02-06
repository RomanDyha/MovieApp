package com.themoviedb.org.di

import com.themoviedb.org.data.data_source.local.MoviesLocalDataSource
import com.themoviedb.org.data.data_source.local.MoviesLocalDataSourceImpl
import com.themoviedb.org.data.data_source.remote.MoviesRemoteDataSource
import com.themoviedb.org.data.data_source.remote.MoviesRemoteDataSourceImpl
import com.themoviedb.org.data.repository.MoviesRepositoryImpl
import com.themoviedb.org.domain.repository.MoviesRepository
import org.koin.dsl.module

val dataModule = module {

    single<MoviesRemoteDataSource> {
        MoviesRemoteDataSourceImpl(moviesApiService = get())
    }

    single<MoviesLocalDataSource> {
        MoviesLocalDataSourceImpl(moviesDao = get())
    }

    single<MoviesRepository> {
        MoviesRepositoryImpl(moviesRemoteDataSource = get(), moviesLocalDataSource = get())
    }

}