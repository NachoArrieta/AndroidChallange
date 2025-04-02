package com.nacho.androidchallange.domain

import com.nacho.androidchallange.data.model.Serie
import com.nacho.androidchallange.data.repository.SeriesRepository
import javax.inject.Inject

class GetSeriesUseCase @Inject constructor(
    private val seriesRepository: SeriesRepository
) {
    suspend operator fun invoke(query: String): List<Serie> = seriesRepository.searchSeries(query)
}

/*
    Hago uso directamente del modelo de datos de la "DataLayer" para no hacer sobre ingeniería
    ya que es una app sencilla donde solo lee datos, de lo contrario, se debería o estaría bueno
    utilizar un modelo de datos ubicado en el "DominioLayer" con su respectivo mapper.
*/
