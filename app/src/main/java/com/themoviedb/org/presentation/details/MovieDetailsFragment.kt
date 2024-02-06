package com.themoviedb.org.presentation.details

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.themoviedb.org.databinding.FragmentMovieDetailsBinding
import com.themoviedb.org.domain.models.DataStatus
import com.themoviedb.org.domain.models.MovieDomain
import com.themoviedb.org.presentation.search.SearchMoviesFragment.Companion.MOVIE_ID_EXTRA_KEY
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MovieDetailsFragment : Fragment() {

    lateinit var binding: FragmentMovieDetailsBinding

    private val viewModel by viewModel<MovieDetailsViewModel> {
        parametersOf(requireActivity().intent.getIntExtra(MOVIE_ID_EXTRA_KEY, 0))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //  http://themoviedb.com/details?id=887870
        if (requireActivity().intent.data != null) {
            val deepLink = requireActivity().intent.data.toString()
            handleDeepLink(deepLink)
        }else{
            // get movie details
            viewModel.getMovieDetails()
        }

        updateUI()
    }

    // display the result of the DB request
    private fun updateUI() {
        binding.apply {
            viewModel.movieDetails.observe(viewLifecycleOwner) {
                when (it.status) {
                    DataStatus.Status.LOADING -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }

                    DataStatus.Status.SUCCESS -> {
                        binding.progressBar.visibility = View.GONE
                        it.data?.let { it1 -> showMovieDetails(it1) }
                    }

                    DataStatus.Status.ERROR -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun showMovieDetails(movieDomain: MovieDomain){
        context?.let { Glide.with(it).load(movieDomain.poster_path).into(binding.moviePoster) }
        binding.movieTitle.text = movieDomain.title
        binding.movieOverview.text = movieDomain.overview
        binding.movieRating.text = "Rating: ${movieDomain.popularity}"
    }

    // get movie id from deeplink
    private fun handleDeepLink(deepLink: String) {
        try {
            val uri = Uri.parse(deepLink)
            val parameter1 = uri.getQueryParameter("id")
            if (parameter1 != null) {
                val movieId = parameter1.toInt()
                viewModel.movieId = movieId
                viewModel.getMovieDetails()
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}