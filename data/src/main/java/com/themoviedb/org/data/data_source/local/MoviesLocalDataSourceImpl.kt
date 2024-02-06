package com.themoviedb.org.data.data_source.local

// implementation LocalDataSource
class MoviesLocalDataSourceImpl(private val moviesDao: MoviesDao): MoviesLocalDataSource {

    override fun getMovieById(movieId: Int): MovieEntity {
        return moviesDao.getMovieById(movieId = movieId)
    }

    override suspend fun saveMovies(moviesList: List<MovieEntity>) {
        moviesDao.insertMovies(moviesList)
    }

}