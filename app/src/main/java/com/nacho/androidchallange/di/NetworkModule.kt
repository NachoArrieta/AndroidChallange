package com.nacho.androidchallange.di

import com.nacho.androidchallange.data.repository.SeriesRepository
import com.nacho.androidchallange.data.repository.SeriesRepositoryImpl
import com.nacho.androidchallange.data.repository.TvMazeApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.tvmaze.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): TvMazeApiService {
        return retrofit.create(TvMazeApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideSeriesRepository(apiService: TvMazeApiService): SeriesRepository {
        return SeriesRepositoryImpl(apiService)
    }

}