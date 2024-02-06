package com.themoviedb.org.presentation.details

import android.os.Bundle
import com.themoviedb.org.R
import com.themoviedb.org.databinding.ActivityFrameBinding
import com.themoviedb.org.presentation.ActivityUtils
import com.themoviedb.org.presentation.BaseActivity

class MovieDetailsActivity : BaseActivity() {

    lateinit var binding: ActivityFrameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFrameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // add MovieDetailsFragment
        var movieDetailsFragment = supportFragmentManager.findFragmentById(R.id.contentFrame)
                as MovieDetailsFragment?
        if (movieDetailsFragment == null) {
            movieDetailsFragment = MovieDetailsFragment()
            ActivityUtils.addFragmentToActivity(
                supportFragmentManager, movieDetailsFragment, R.id.contentFrame)
        }
    }
}