package com.themoviedb.org.domain.models

data class MovieDomain(
    // movie ID
    var id: Int,
    // poster download link
    var poster_path: String,
    // movie description
    var overview: String,
    // movie title
    var title: String,
    // movie rating
    var popularity: Float
)

