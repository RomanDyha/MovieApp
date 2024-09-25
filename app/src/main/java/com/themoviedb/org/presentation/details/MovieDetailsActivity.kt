package com.themoviedb.org.presentation.details

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
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
        initActionBar()
    }

    private fun initActionBar(){
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)  // Show the "Back" button
            setDisplayShowHomeEnabled(true)  // Enable display of the icon
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                // Return to the previous activity
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}