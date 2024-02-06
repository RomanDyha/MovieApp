package com.themoviedb.org.presentation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

object ActivityUtils {
    /**
     * The `fragment` is added to the container view with id `frameId`. The operation is
     * performed by the `fragmentManager`.
     */
    @JvmStatic
    fun addFragmentToActivity(
        fragmentManager: FragmentManager,
        fragment: Fragment?,
        frameId: Int
    ) {
        val transaction = fragmentManager.beginTransaction()
        fragment?.let { transaction.replace(frameId, it) }
        transaction.commit()
    }
}