package com.example.nawadatatest_option1_themoviedb.network

import com.example.nawadatatest_option1_themoviedb.model.Genres
import com.example.nawadatatest_option1_themoviedb.model.Movie
import retrofit2.http.GET
import retrofit2.http.Query


interface FilmAPIService {

    @GET("genre/movie/list?api_key=ca0d93ea30b7015390f9ae8d859f6863")
    suspend fun getMovieGenres(): Genres

    @GET("movie/now_playing?api_key=ca0d93ea30b7015390f9ae8d859f6863")
    suspend fun getMovieNowPlaying(): Movie

    // Additional API call to get movies by genre
    @GET("discover/movie?api_key=ca0d93ea30b7015390f9ae8d859f6863")
    suspend fun getMoviesByGenre(@Query("with_genres") genreId: Int): Movie

}
