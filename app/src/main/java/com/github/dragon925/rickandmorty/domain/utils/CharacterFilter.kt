package com.github.dragon925.rickandmorty.domain.utils

import java.util.EnumMap

class CharacterFilter : FilterBuilder<Filters.Character> {

    private val filters = EnumMap<Filters.Character, String>(Filters.Character::class.java)

    var name: String?
        set(value) { setter(Filters.Character.NAME, value) }
        get() = filters[Filters.Character.NAME]

    var status: String?
        set(value) { setter(Filters.Character.STATUS, value) }
        get() = filters[Filters.Character.STATUS]

    var species: String?
        set(value) { setter(Filters.Character.SPECIES, value) }
        get() = filters[Filters.Character.SPECIES]

    var type: String?
        set(value) { setter(Filters.Character.TYPE, value) }
        get() = filters[Filters.Character.TYPE]

    var gender: String?
        set(value) { setter(Filters.Character.GENDER, value) }
        get() = filters[Filters.Character.GENDER]

    override fun build(): EnumMap<Filters.Character, String> = filters

    private fun setter(filter: Filters.Character, value: String?) {
        if (value.isNullOrBlank()) {
            filters.remove(filter)
        } else {
            filters[filter] = value
        }
    }
}