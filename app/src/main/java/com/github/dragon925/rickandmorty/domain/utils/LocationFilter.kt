package com.github.dragon925.rickandmorty.domain.utils

import java.util.EnumMap

class LocationFilter : FilterBuilder<Filters.Location> {

    private val filters = EnumMap<Filters.Location, String>(Filters.Location::class.java)

    var name: String?
        set(value) { setter(Filters.Location.NAME, value) }
        get() = filters[Filters.Location.NAME]

    var type: String?
        set(value) { setter(Filters.Location.TYPE, value) }
        get() = filters[Filters.Location.TYPE]

    var dimension: String?
        set(value) { setter(Filters.Location.DIMENSION, value) }
        get() = filters[Filters.Location.DIMENSION]


    override fun build(): EnumMap<Filters.Location, String> = filters

    private fun setter(filter: Filters.Location, value: String?) {
        if (value.isNullOrBlank()) {
            filters.remove(filter)
        } else {
            filters[filter] = value
        }
    }
}