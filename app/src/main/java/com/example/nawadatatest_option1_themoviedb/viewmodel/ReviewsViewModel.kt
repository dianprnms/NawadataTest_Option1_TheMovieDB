package com.example.nawadatatest_option1_themoviedb.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nawadatatest_option1_themoviedb.model.Detail
import com.example.nawadatatest_option1_themoviedb.model.Reviews
import com.example.nawadatatest_option1_themoviedb.network.FilmAPIService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ReviewsViewModel @Inject constructor(private val filmAPIService: FilmAPIService) : ViewModel() {

    private val _reviewsData = MutableLiveData<Reviews?>()
    val reviewsData: MutableLiveData<Reviews?>
        get() {
            val _reviewsData1 = _reviewsData
            return _reviewsData1
        }

    suspend fun getReviews(movieId: Int) {
        withContext(Dispatchers.IO) {
            try {
                val response = filmAPIService.getReviewsMovie(movieId).execute()
                if (response.isSuccessful) {
                    _reviewsData.postValue(response.body())
                } else {
                    _reviewsData.postValue(null)
                }
            } catch (e: Exception) {
                _reviewsData.postValue(null)
            }

        }
    }
}