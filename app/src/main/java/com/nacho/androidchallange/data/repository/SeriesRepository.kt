package com.nacho.androidchallange.data.repository

import com.nacho.androidchallange.data.model.Episode
import com.nacho.androidchallange.data.model.Serie

interface SeriesRepository {
    suspend fun searchSeries(query: String): List<Serie>
    suspend fun getEpisodes(seriesId: Int): List<Episode>
}