package com.example.nawadatatest_option1_themoviedb.dependencies

import com.example.nawadatatest_option1_themoviedb.network.FilmAPIService
import com.example.nawadatatest_option1_themoviedb.viewmodel.VideoViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object VideoModule {

    @Provides
    @ViewModelScoped
    fun provideVideoViewModel(filmAPIService: FilmAPIService): VideoViewModel {
        return VideoViewModel(filmAPIService)
    }
}
