package com.example.nawadatatest_option1_themoviedb.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.nawadatatest_option1_themoviedb.databinding.FragmentDetailBinding
import com.example.nawadatatest_option1_themoviedb.viewmodel.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var detailViewModel: DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        detailViewModel = ViewModelProvider(this).get(DetailViewModel::class.java)

        val movieId = arguments?.getInt("movieId", -1)
        if (movieId != null && movieId != -1) {
            fetchData(movieId)
        }
    }

    private fun fetchData(movieId: Int) {
        lifecycleScope.launch(Dispatchers.Main) {
            detailViewModel.getMovieDetail(movieId)
            observeViewModel()
        }
    }

    private fun observeViewModel() {
        detailViewModel.detailData.observe(viewLifecycleOwner, { detail ->
            if (detail != null) {
                binding.judulDetail.text = detail.title
                binding.textDetail.text = detail.overview
                Glide.with(this)
                    .load("https://image.tmdb.org/t/p/w500${detail.posterPath}")
                    .into(binding.imageDetail)
                binding.textDetail.setText(detail.overview)
            } else {
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
