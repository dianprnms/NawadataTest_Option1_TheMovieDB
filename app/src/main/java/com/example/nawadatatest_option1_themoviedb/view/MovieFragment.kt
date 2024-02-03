package com.example.nawadatatest_option1_themoviedb.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nawadatatest_option1_themoviedb.R
import com.example.nawadatatest_option1_themoviedb.adapter.NowPlayingAdapter
import com.example.nawadatatest_option1_themoviedb.databinding.FragmentMovieBinding
import com.example.nawadatatest_option1_themoviedb.viewmodel.NowPlayingViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieFragment : Fragment() {
    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!

    private lateinit var nowPlayingViewModel: NowPlayingViewModel
    private lateinit var nowPlayingAdapter: NowPlayingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nowPlayingViewModel = ViewModelProvider(this).get(NowPlayingViewModel::class.java)
        nowPlayingAdapter = NowPlayingAdapter(emptyList()) // Initialize with an empty list

        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvMovie.layoutManager = layoutManager
        binding.rvMovie.adapter = nowPlayingAdapter

        observeViewModel()

        val genreId = arguments?.getInt("genreId", -1)
        if (genreId != null && genreId != -1) {
            fetchData(genreId)
        }
    }

    private fun observeViewModel() {
        nowPlayingViewModel.getNowPlayingData().observe(viewLifecycleOwner, { movies ->
            movies?.let {
                nowPlayingAdapter.updateResult(it)
            }
        })

        nowPlayingAdapter.onClick = { selectedMovie ->
            val bundle = Bundle().apply {
                putInt("movieId", selectedMovie.id!!.toInt())
            }
            findNavController().navigate(R.id.action_movieFragment_to_detailFragment, bundle)
        }
    }

    private fun fetchData(genreId: Int) {
        lifecycleScope.launch(Dispatchers.Main) {
            nowPlayingViewModel.callApiNowPlayingByGenre(genreId)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
