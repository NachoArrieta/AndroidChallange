package com.nacho.androidchallange.domain

import com.nacho.androidchallange.data.model.Episode
import com.nacho.androidchallange.data.repository.SeriesRepository
import javax.inject.Inject

class GetEpisodesUseCase @Inject constructor(
    private val seriesRepository: SeriesRepository
) {
    suspend operator fun invoke(seriesId: Int): List<Episode> =
        seriesRepository.getEpisodes(seriesId)
}

/*
    Hago uso directamente del modelo de datos de la "DataLayer" para no hacer sobre ingeniería
    ya que es una app sencilla donde solo lee datos, de lo contrario, se debería o estaría bueno
    utilizar un modelo de datos ubicado en el "DominioLayer" con su respectivo mapper.
*/
