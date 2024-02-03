package com.example.nawadatatest_option1_themoviedb.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitClient {
    private const val BASE_URL = "https://api.themoviedb.org/3/"
    private const val API_KEY = "ca0d93ea30b7015390f9ae8d859f6863"

    private val logging: HttpLoggingInterceptor
        get() {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            return httpLoggingInterceptor.apply {
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            }
        }

    private val apiKeyInterceptor = Interceptor { chain ->
        val original = chain.request()
        val originalHttpUrl = original.url

        val url = originalHttpUrl.newBuilder()
            .addQueryParameter("api_key", API_KEY)
            .build()

        val requestBuilder = original.newBuilder()
            .url(url)

        val request = requestBuilder.build()
        chain.proceed(request)
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(apiKeyInterceptor) // Tambahkan Interceptor untuk menambahkan API key
        .addInterceptor(logging)
        .build()

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

    @Singleton
    @Provides
    fun provideFilmApiService(retrofit: Retrofit): FilmAPIService =
        retrofit.create(FilmAPIService::class.java)
}
