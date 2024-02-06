package com.themoviedb.org.presentation.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.themoviedb.org.domain.models.DataStatus
import com.themoviedb.org.domain.models.MovieDomain
import com.themoviedb.org.domain.use_cases.GetMovieDetailsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieDetailsViewModel(private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
                            var movieId: Int) : ViewModel() {

    private val _movieDetails = MutableLiveData<DataStatus<MovieDomain>>()
    val movieDetails: LiveData<DataStatus<MovieDomain>>
        get() = _movieDetails

    fun getMovieDetails() = viewModelScope.launch(Dispatchers.IO) {
        getMovieDetailsUseCase.invoke(movieId = movieId).collect {
            _movieDetails.postValue(it)
        }
    }

}