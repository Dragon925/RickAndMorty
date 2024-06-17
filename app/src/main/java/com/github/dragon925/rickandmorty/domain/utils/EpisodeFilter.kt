package com.github.dragon925.rickandmorty.domain.utils

import java.util.EnumMap

class EpisodeFilter : FilterBuilder<Filters.Episode> {

    private val filters = EnumMap<Filters.Episode, String>(Filters.Episode::class.java)

    var name: String?
        set(value) { setter(Filters.Episode.NAME, value) }
        get() = filters[Filters.Episode.NAME]

    var episode: String?
        set(value) { setter(Filters.Episode.EPISODE, value) }
        get() = filters[Filters.Episode.EPISODE]


    override fun build(): EnumMap<Filters.Episode, String> = filters

    private fun setter(filter: Filters.Episode, value: String?) {
        if (value.isNullOrBlank()) {
            filters.remove(filter)
        } else {
            filters[filter] = value
        }
    }
}