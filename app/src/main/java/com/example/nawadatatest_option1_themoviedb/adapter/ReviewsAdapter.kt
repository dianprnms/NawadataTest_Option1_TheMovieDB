package com.example.nawadatatest_option1_themoviedb.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nawadatatest_option1_themoviedb.databinding.ItemReviewsBinding
import com.example.nawadatatest_option1_themoviedb.model.ResultXX

class ReviewsAdapter(private var listReviews: List<ResultXX>) :
    RecyclerView.Adapter<ReviewsAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemReviewsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemReviewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = listReviews.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val review = listReviews[position]
        holder.binding.author.text = review.author
        holder.binding.review.text = review.content
    }

    fun updateReviews(newList: List<ResultXX>) {
        listReviews = newList
        notifyDataSetChanged()
        Log.d("ReviewsAdapter", "Updated reviews: $newList")
    }

}
