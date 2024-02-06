package com.themoviedb.org.di

import com.themoviedb.org.presentation.details.MovieDetailsViewModel
import com.themoviedb.org.presentation.search.SearchMoviesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel {
        SearchMoviesViewModel(searchMoviesUseCase = get())
    }

    viewModel { (movieId: Int) ->
        MovieDetailsViewModel(
            getMovieDetailsUseCase = get(),
            movieId = movieId
        )
    }

}