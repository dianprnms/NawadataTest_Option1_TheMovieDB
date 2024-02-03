package com.example.nawadatatest_option1_themoviedb.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nawadatatest_option1_themoviedb.model.Genre
import com.example.nawadatatest_option1_themoviedb.model.Genres
import com.example.nawadatatest_option1_themoviedb.network.FilmAPIService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class GenreViewModel @Inject constructor(private val filmAPIService: FilmAPIService) : ViewModel() {

    private val TAG = "GenreViewModel"

    var liveGenreData: MutableLiveData<List<Genre>?> = MutableLiveData()

    private var selectedGenreId: Int? = null

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

    private val selectedGenreIdLiveData: MutableLiveData<Int?> = MutableLiveData()

    fun setSelectedGenreId(genreId: Int) {
        selectedGenreId = genreId
        selectedGenreIdLiveData.value = genreId
    }

    fun observeSelectedGenreId(): MutableLiveData<Int?> {
        return selectedGenreIdLiveData
    }
}
