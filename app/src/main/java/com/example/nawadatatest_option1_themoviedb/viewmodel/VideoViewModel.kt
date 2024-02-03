package com.example.nawadatatest_option1_themoviedb.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nawadatatest_option1_themoviedb.model.ResultXXX
import com.example.nawadatatest_option1_themoviedb.network.FilmAPIService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class VideoViewModel @Inject constructor(private val filmAPIService: FilmAPIService) : ViewModel() {

    private val _videoData = MutableLiveData<List<ResultXXX>?>()
    val videoData: MutableLiveData<List<ResultXXX>?>
        get() = _videoData

    suspend fun getVideo(movieId: Int) {
        withContext(Dispatchers.IO) {
            try {
                val response = filmAPIService.getVideo(movieId).execute()
                if (response.isSuccessful) {
                    withContext(Dispatchers.Main) {
                        _videoData.postValue(response.body()?.results)
                    }
                } else {
                    // Log error or handle accordingly
                    withContext(Dispatchers.Main) {
                        _videoData.postValue(null)
                    }
                }
            } catch (e: Exception) {
                // Log error or handle accordingly
                withContext(Dispatchers.Main) {
                    _videoData.postValue(null)
                }
            }
        }
    }

}
