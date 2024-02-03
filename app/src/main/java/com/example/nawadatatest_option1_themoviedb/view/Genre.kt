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
import com.example.nawadatatest_option1_themoviedb.adapter.GenreAdapter
import com.example.nawadatatest_option1_themoviedb.databinding.FragmentGenreBinding
import com.example.nawadatatest_option1_themoviedb.viewmodel.GenreViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class Genre : Fragment() {

    private lateinit var genreViewModel: GenreViewModel
    private var _binding: FragmentGenreBinding? = null
    private val binding get() = _binding!!

    private lateinit var genreAdapter: GenreAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGenreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        genreViewModel = ViewModelProvider(this)[GenreViewModel::class.java]
        genreAdapter = GenreAdapter(emptyList()) // Initialize with an empty list

        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvGenre.layoutManager = layoutManager
        binding.rvGenre.adapter = genreAdapter

        observeViewModel()
        fetchData()
    }

    private fun observeViewModel() {
        genreViewModel.getGenreData().observe(viewLifecycleOwner) { genres ->
            genres?.let {
                genreAdapter.updateGenres(it)
            }
        }

        genreAdapter.onClick = { selectedGenre ->
            val bundle = Bundle().apply {
                putInt("genreId", selectedGenre.id!!.toInt())
            }
            findNavController().navigate(R.id.action_genre_to_movieFragment, bundle)
        }
    }

    private fun fetchData() {
        lifecycleScope.launch(Dispatchers.Main) {
            genreViewModel.callApiGenres()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
