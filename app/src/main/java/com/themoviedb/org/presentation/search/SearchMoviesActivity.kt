package com.themoviedb.org.presentation.search

import android.os.Bundle
import com.themoviedb.org.R
import com.themoviedb.org.databinding.ActivityFrameBinding
import com.themoviedb.org.presentation.ActivityUtils
import com.themoviedb.org.presentation.BaseActivity

class SearchMoviesActivity : BaseActivity() {

    lateinit var binding: ActivityFrameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFrameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // add SearchMoviesFragment
        var searchMoviesFragment = supportFragmentManager.findFragmentById(R.id.contentFrame)
                as SearchMoviesFragment?
        if (searchMoviesFragment == null) {
            searchMoviesFragment = SearchMoviesFragment()
            ActivityUtils.addFragmentToActivity(
                supportFragmentManager, searchMoviesFragment, R.id.contentFrame)
        }
    }

}