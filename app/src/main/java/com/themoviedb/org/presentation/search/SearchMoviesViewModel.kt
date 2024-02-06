package com.themoviedb.org.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.themoviedb.org.domain.models.DataStatus
import com.themoviedb.org.domain.models.MovieDomain
import com.themoviedb.org.domain.use_cases.SearchMoviesUseCase
import kotlinx.coroutines.launch

class SearchMoviesViewModel(private val searchMoviesUseCase: SearchMoviesUseCase) : ViewModel() {

    private val _moviesList = MutableLiveData<DataStatus<List<MovieDomain>>>()
    val moviesList: LiveData<DataStatus<List<MovieDomain>>>
        get() = _moviesList

    fun searchMovies(query: String) = viewModelScope.launch {
        if (searchMoviesUseCase.isInputDataCorrectly())
            // received the result from the API
            searchMoviesUseCase.invoke(query).collect {
                _moviesList.value = it
            }
    }

}