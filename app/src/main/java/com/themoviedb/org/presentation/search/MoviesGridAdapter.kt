package com.themoviedb.org.presentation.search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.bumptech.glide.Glide
import com.themoviedb.org.databinding.MovieItemBinding
import com.themoviedb.org.domain.models.MovieDomain

class MoviesGridAdapter(val context: Context?) : BaseAdapter() {

    private var items: List<MovieDomain> = listOf()

    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(position: Int): Any? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val holder: ViewHolder
        if (convertView == null) {
            val itemBinding: MovieItemBinding =
                MovieItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false)
            holder = ViewHolder(itemBinding)
            holder.view = itemBinding.getRoot()
            holder.view.setTag(holder)
        } else {
            holder = convertView.getTag() as ViewHolder
        }
        holder.binding.movieTitle.text = items[position].title
        holder.binding.movieOverview.text = items[position].overview
        context?.let {
            Glide.with(it).load(items[position].poster_path).into(holder.binding.moviePoster)
        }
        return holder.view
    }

    internal class ViewHolder(binding: MovieItemBinding) {
         var view: View
         var binding: MovieItemBinding

        init {
            view = binding.getRoot()
            this.binding = binding
        }
    }

    fun updateItems(items: List<MovieDomain>){
        this.items = items
        notifyDataSetChanged()
    }
}