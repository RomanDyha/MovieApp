package com.themoviedb.org.presentation.search

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.themoviedb.org.databinding.FragmentSearchMoviesBinding
import com.themoviedb.org.domain.models.DataStatus
import com.themoviedb.org.domain.models.MovieDomain
import com.themoviedb.org.presentation.details.MovieDetailsActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchMoviesFragment : Fragment() {

    private var binding: FragmentSearchMoviesBinding? = null
    private val viewModel by viewModel<SearchMoviesViewModel>()
    private var adapter: MoviesGridAdapter? = null

    companion object { val MOVIE_ID_EXTRA_KEY = "MOVIE_ID_EXTRA_KEY" }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentSearchMoviesBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        performSearch()
        updateUI()
    }

    private fun performSearch() {
        binding?.searchInputView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                viewModel.searchMovies(newText)
                return true
            }
        })
    }

    // display the result of the API request
    private fun updateUI() {
        binding.apply {
            viewModel.moviesList.observe(viewLifecycleOwner) {
                when (it.status) {
                    DataStatus.Status.LOADING -> {
                        binding?.progressBar?.visibility = VISIBLE
                    }

                    DataStatus.Status.SUCCESS -> {
                        binding?.progressBar?.visibility = GONE
                        it.data?.let { it1 -> showMoviesList(it1) }
                    }

                    DataStatus.Status.ERROR -> {
                        binding?.progressBar?.visibility = GONE
                        Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    // Сreate GridAdapter
    private fun showMoviesList(moviesList: List<MovieDomain>) {
        if(adapter == null) {
            adapter = MoviesGridAdapter(context)
            binding?.moviesGridView?.adapter = adapter
            binding?.moviesGridView?.setOnItemClickListener { parent, view, position, id ->
               openMovieDetailsActivity(moviesList[position].id)
            }
        }

        adapter!!.updateItems(moviesList)
    }

    // show movie details screen
    private fun openMovieDetailsActivity(movieId: Int) {
        val intent = Intent(requireActivity(), MovieDetailsActivity::class.java)
        intent.putExtra(MOVIE_ID_EXTRA_KEY, movieId)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}
