package com.nacho.androidchallange.data.repository

import com.nacho.androidchallange.data.model.ApiResponse
import com.nacho.androidchallange.data.model.Episode
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TvMazeApiService {
    @GET("search/shows")
    suspend fun searchSeries(@Query("q") query: String): List<ApiResponse>

    @GET("shows/{id}/episodes")
    suspend fun getEpisodes(@Path("id") seriesId: Int): List<Episode>
}
