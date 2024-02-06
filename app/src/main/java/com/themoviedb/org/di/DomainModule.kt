package com.themoviedb.org.di

import com.themoviedb.org.domain.use_cases.GetMovieDetailsUseCase
import com.themoviedb.org.domain.use_cases.SearchMoviesUseCase
import org.koin.dsl.module

val domainModule = module {

    factory {
        SearchMoviesUseCase(moviesRepository = get())
    }

    factory {
        GetMovieDetailsUseCase(moviesRepository = get())
    }

}