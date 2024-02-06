package com.themoviedb.org.data.data_source.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.themoviedb.org.data.BuildConfig
import com.themoviedb.org.domain.models.MovieDomain
// table to store information about movies
@Entity(tableName = "movie")
data class MovieEntity(
    @PrimaryKey
    val id: Int,
    val poster_path: String,
    val overview: String,
    val title: String,
    val popularity: Float
)

// convert MovieEntity to domain model
fun MovieEntity.toDomainModel(): MovieDomain {
    return MovieDomain(
            id = id,
            poster_path = BuildConfig.IMAGE_URL + poster_path, // Here we are adding the base url to the poster_path
            overview = overview,
            title = title,
            popularity = popularity
        )
}








