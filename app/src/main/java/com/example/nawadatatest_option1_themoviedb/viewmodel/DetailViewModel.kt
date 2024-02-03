package com.example.nawadatatest_option1_themoviedb.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nawadatatest_option1_themoviedb.model.Detail
import com.example.nawadatatest_option1_themoviedb.network.FilmAPIService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val filmAPIService: FilmAPIService) : ViewModel() {

    private val _detailData = MutableLiveData<Detail?>()
    val detailData: MutableLiveData<Detail?>
        get() = _detailData

    suspend fun getMovieDetail(movieId: Int) {
        withContext(Dispatchers.IO) {
            try {
                val response = filmAPIService.getMovieDetail(movieId).execute()
                if (response.isSuccessful) {
                    _detailData.postValue(response.body())
                } else {
                    _detailData.postValue(null)
                }
            } catch (e: Exception) {
                _detailData.postValue(null)
            }
        }
    }
}
