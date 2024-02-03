package com.example.nawadatatest_option1_themoviedb.network

import com.example.nawadatatest_option1_themoviedb.model.Detail
import com.example.nawadatatest_option1_themoviedb.model.Genres
import com.example.nawadatatest_option1_themoviedb.model.Movie
import com.example.nawadatatest_option1_themoviedb.model.Reviews
import com.example.nawadatatest_option1_themoviedb.model.Video
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface FilmAPIService {

    @GET("genre/movie/list?api_key=ca0d93ea30b7015390f9ae8d859f6863")
    suspend fun getMovieGenres(): Genres

    @GET("movie/now_playing?api_key=ca0d93ea30b7015390f9ae8d859f6863")
    suspend fun getMovieNowPlaying(): Movie

    @GET("discover/movie?api_key=ca0d93ea30b7015390f9ae8d859f6863")
    suspend fun getMoviesByGenre(@Query("with_genres") genreId: Int): Movie

    @GET("movie/{movieId}?api_key=ca0d93ea30b7015390f9ae8d859f6863")
    fun getMovieDetail(
        @Path("movieId") movieId:Int,
    ): Call<Detail>

    @GET("movie/{movieId}/reviews?api_key=ca0d93ea30b7015390f9ae8d859f6863")
    fun getReviewsMovie(
        @Path("movieId") movieId: Int,
    ): Call<Reviews>

    @GET("movie/{movieId}/videos?api_key=ca0d93ea30b7015390f9ae8d859f6863")
    fun getVideo(
        @Path("movieId") movieId: Int,
    ): Call<Video>



}
