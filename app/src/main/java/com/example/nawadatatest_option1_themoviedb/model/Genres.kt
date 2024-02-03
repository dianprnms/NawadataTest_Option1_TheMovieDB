package com.example.nawadatatest_option1_themoviedb.model


import com.google.gson.annotations.SerializedName

data class Genres(
    @SerializedName("genres")
    val genres: List<Genre?>?
)