package com.nacho.androidchallange.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nacho.androidchallange.data.model.Episode
import com.nacho.androidchallange.data.model.Serie
import com.nacho.androidchallange.domain.GetEpisodesUseCase
import com.nacho.androidchallange.domain.GetSeriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeriesViewModel @Inject constructor(
    private val getSeriesUseCase: GetSeriesUseCase,
    private val getEpisodesUseCase: GetEpisodesUseCase
) : ViewModel() {

    private val _seriesList = MutableStateFlow<List<Serie>>(emptyList())
    val seriesList: StateFlow<List<Serie>> = _seriesList.asStateFlow()

    private val _episodes = MutableStateFlow<List<Episode>>(emptyList())
    val episodes: StateFlow<List<Episode>> = _episodes.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    fun searchSeries(query: String) {
        if (query.isBlank()) {
            clearResults()
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            try {
                val result = getSeriesUseCase(query)
                _seriesList.value = result
            } catch (e: Exception) {
                _errorMessage.value = "Error al buscar series: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getEpisodes(seriesId: Int) {
        viewModelScope.launch {
            try {
                _episodes.value = getEpisodesUseCase(seriesId)
            } catch (e: Exception) {
                _episodes.value = emptyList()
            }
        }
    }

    fun clearResults() {
        _seriesList.value = emptyList()
    }

}