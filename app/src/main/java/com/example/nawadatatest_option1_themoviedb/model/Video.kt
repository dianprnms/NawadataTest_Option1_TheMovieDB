package com.example.nawadatatest_option1_themoviedb.model


import com.google.gson.annotations.SerializedName

data class Video(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("results")
    val results: List<ResultXXX>?
)