package com.example.nawadatatest_option1_themoviedb.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.nawadatatest_option1_themoviedb.databinding.ItemMovieBinding
import com.example.nawadatatest_option1_themoviedb.model.ResultX

class NowPlayingAdapter (private var listMovieNowPlaying: List<ResultX>): RecyclerView.Adapter<NowPlayingAdapter.ViewHolder>()  {
    var onClick : ((ResultX)->Unit)? = null

    class ViewHolder (var binding: ItemMovieBinding): RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listMovieNowPlaying.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.judulFilm.text = listMovieNowPlaying[position].title
        holder.binding.tglFilm.text = listMovieNowPlaying[position].releaseDate
        Glide.with(holder.itemView.context)
            .load("https://image.tmdb.org/t/p/w500${listMovieNowPlaying[position].posterPath}")
            .into(holder.binding.imgHome)

        holder.binding.cvItemMovie.setOnClickListener {
            onClick!!.invoke(listMovieNowPlaying[position])
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateResult(newResultX: List<ResultX>) {
        listMovieNowPlaying = newResultX
        notifyDataSetChanged()
    }
}