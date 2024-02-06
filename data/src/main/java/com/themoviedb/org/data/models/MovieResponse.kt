package com.themoviedb.org.data.models

import com.themoviedb.org.data.BuildConfig
import com.themoviedb.org.data.data_source.local.MovieEntity
import com.themoviedb.org.domain.models.MovieDomain

// Response model
data class MoviesResponse(
    val results: List<Movie>,
)

data class Movie(
    val id: Int,
    val poster_path: String,
    val overview: String,
    val title: String,
    val popularity: Float
)

fun List<Movie>.toDomainModel(): List<MovieDomain> {
    return map {
        MovieDomain(
            id = it.id,
            poster_path = BuildConfig.IMAGE_URL + it.poster_path, // Here we are adding the base url to the poster_path
            overview = it.overview,
            title = it.title,
            popularity = it.popularity
        )
    }
}

fun List<MovieDomain>.toEntityModel(): List<MovieEntity> {
    return map {
        MovieEntity(
            id = it.id,
            poster_path = it.poster_path,
            overview = it.overview,
            title = it.title,
            popularity = it.popularity
        )
    }
}
