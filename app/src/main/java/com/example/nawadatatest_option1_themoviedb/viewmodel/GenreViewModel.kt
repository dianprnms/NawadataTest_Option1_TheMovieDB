package com.example.nawadatatest_option1_themoviedb.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nawadatatest_option1_themoviedb.model.Genre
import com.example.nawadatatest_option1_themoviedb.network.FilmAPIService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class GenreViewModel @Inject constructor(private val filmAPIService: FilmAPIService) : ViewModel() {

    private val TAG = "GenreViewModel"

    private var liveGenreData: MutableLiveData<List<Genre>?> = MutableLiveData()

    fun getGenreData(): MutableLiveData<List<Genre>?> {
        return liveGenreData
    }

    suspend fun callApiGenres() {
        withContext(Dispatchers.IO) {
            try {
                val response = filmAPIService.getMovieGenres()
                if (response.genres!!.isNotEmpty()) {
                    liveGenreData.postValue(response.genres as List<Genre>?)
                    Log.d(TAG, "callApiGenres: ${response.genres.size}")
                } else {
                    Log.d(TAG, "callApiGenres: Empty response")
                    liveGenreData.postValue(null)
                }
            } catch (e: Exception) {
                Log.d(TAG, "callApiGenres: ${e.message}")
                liveGenreData.postValue(null)
            }
        }
    }

}
