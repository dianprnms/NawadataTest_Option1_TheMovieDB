package com.example.nawadatatest_option1_themoviedb.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.nawadatatest_option1_themoviedb.adapter.ReviewsAdapter
import com.example.nawadatatest_option1_themoviedb.databinding.FragmentDetailBinding
import com.example.nawadatatest_option1_themoviedb.viewmodel.DetailViewModel
import com.example.nawadatatest_option1_themoviedb.viewmodel.ReviewsViewModel
import com.example.nawadatatest_option1_themoviedb.viewmodel.VideoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var detailViewModel: DetailViewModel
    private lateinit var reviewsViewModel: ReviewsViewModel
    private lateinit var reviewsAdapter: ReviewsAdapter
    private lateinit var videoViewModel: VideoViewModel
    private var videoKey: String? = null

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
        reviewsViewModel = ViewModelProvider(this).get(ReviewsViewModel::class.java)
        reviewsAdapter = ReviewsAdapter(emptyList())
        videoViewModel = ViewModelProvider(this).get(VideoViewModel::class.java)


        val movieId = arguments?.getInt("movieId", -1)
        if (movieId != null && movieId != -1) {
            fetchData(movieId)
            fetchReviews(movieId)
            fetchVideo(movieId)
        }

        setupRecyclerView()

        observeViewModel()
        observeReviewsViewModel()
        observeVideoViewModel()
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvMovie.layoutManager = layoutManager
        binding.rvMovie.adapter = reviewsAdapter
    }


    private fun fetchData(movieId: Int) {
        lifecycleScope.launch(Dispatchers.Main) {
            detailViewModel.getMovieDetail(movieId)
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
            } else {
                // Handle error or empty response
            }
        })
    }

    private fun fetchReviews(movieId: Int) {
        lifecycleScope.launch(Dispatchers.Main) {
            Log.d("DetailFragment", "Fetching reviews for movieId: $movieId")
            reviewsViewModel.getReviews(movieId)
        }
    }

    private fun observeReviewsViewModel() {
        reviewsViewModel.reviewsData.observe(viewLifecycleOwner, { reviews ->
            reviews?.let {
                Log.d("DetailFragment", "Received reviews: $reviews")
                reviewsAdapter.updateReviews(it.results.orEmpty())
            }
        })
    }

    private fun fetchVideo(movieId: Int) {
        lifecycleScope.launch(Dispatchers.Main) {
            videoViewModel.getVideo(movieId)
        }
    }

    private fun observeVideoViewModel() {
        videoViewModel.videoData.observe(viewLifecycleOwner, { videoResults ->
            videoResults?.let {
                if (it.isNotEmpty()) {
                    videoKey = it[0].key
                    loadYouTubeVideo()
                } else {
                    // Handle empty video response
                }
            }
        })
    }

    private fun loadYouTubeVideo() {
        val youtubeVideoUrl = "https://www.youtube.com/embed/$videoKey"
        binding.webViewSaya.settings.javaScriptEnabled = true
        binding.webViewSaya.webChromeClient = WebChromeClient()
        binding.webViewSaya.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                return false
            }
        }
        binding.webViewSaya.loadUrl(youtubeVideoUrl)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
