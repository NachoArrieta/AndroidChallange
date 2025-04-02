package com.nacho.androidchallange.data.repository

import com.nacho.androidchallange.data.model.Episode
import com.nacho.androidchallange.data.model.Serie
import javax.inject.Inject

class SeriesRepositoryImpl @Inject constructor(
    private val apiService: TvMazeApiService
) : SeriesRepository {

    override suspend fun searchSeries(query: String): List<Serie> {
        return try {
            apiService.searchSeries(query).map { it.show }
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun getEpisodes(seriesId: Int): List<Episode> {
        return try {
            apiService.getEpisodes(seriesId)
        } catch (e: Exception) {
            emptyList()
        }
    }

}