package com.example.nawadatatest_option1_themoviedb.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nawadatatest_option1_themoviedb.model.ResultX
import com.example.nawadatatest_option1_themoviedb.network.FilmAPIService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class NowPlayingViewModel @Inject constructor(private val filmAPIService: FilmAPIService) : ViewModel() {

    private val TAG = "NowPlayingViewModel"

    var liveNowPlayingData: MutableLiveData<List<ResultX>?> = MutableLiveData()

    fun getNowPlayingData(): MutableLiveData<List<ResultX>?> {
        return liveNowPlayingData
    }

    suspend fun callApiNowPlaying() {
        withContext(Dispatchers.IO) {
            try {
                val response = filmAPIService.getMovieNowPlaying()
                response.results?.let {
                    if (it.isNotEmpty()) {
                        liveNowPlayingData.postValue(it)
                        Log.d(TAG, "callApiNowPlaying: ${it.size}")
                    } else {
                        Log.d(TAG, "callApiNowPlaying: Empty response")
                        liveNowPlayingData.postValue(null)
                    }
                }
            } catch (e: HttpException) {
                Log.d(TAG, "callApiNowPlaying: ${e.message}")
                liveNowPlayingData.postValue(null)
            } catch (e: Exception) {
                Log.d(TAG, "callApiNowPlaying: ${e.message}")
                liveNowPlayingData.postValue(null)
            }
        }
    }

    suspend fun callApiNowPlayingByGenre(genreId: Int) {
        withContext(Dispatchers.IO) {
            try {
                val response = filmAPIService.getMoviesByGenre(genreId)
                response.results?.let {
                    if (it.isNotEmpty()) {
                        liveNowPlayingData.postValue(it)
                        Log.d(TAG, "callApiNowPlayingByGenre: ${it.size}")
                    } else {
                        Log.d(TAG, "callApiNowPlayingByGenre: Empty response")
                        liveNowPlayingData.postValue(null)
                    }
                }
            } catch (e: HttpException) {
                Log.d(TAG, "callApiNowPlayingByGenre: ${e.message}")
                liveNowPlayingData.postValue(null)
            } catch (e: Exception) {
                Log.d(TAG, "callApiNowPlayingByGenre: ${e.message}")
                liveNowPlayingData.postValue(null)
            }
        }
    }


}
