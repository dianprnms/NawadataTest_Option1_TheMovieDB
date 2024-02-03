package com.example.nawadatatest_option1_themoviedb.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nawadatatest_option1_themoviedb.databinding.ItemMovieBinding
import com.example.nawadatatest_option1_themoviedb.model.ResultX

class NowPlayingAdapter (var listMovieNowPlaying: List<ResultX>): RecyclerView.Adapter<NowPlayingAdapter.ViewHolder>()  {

    class ViewHolder (var binding: ItemMovieBinding): RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listMovieNowPlaying.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.judulFilm.text = listMovieNowPlaying[position].title
    }

    fun updateResult(newResultX: List<ResultX>) {
        listMovieNowPlaying = newResultX
        notifyDataSetChanged()
    }
}