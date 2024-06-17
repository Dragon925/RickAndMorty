package com.github.dragon925.rickandmorty.domain.utils

import java.util.EnumMap

sealed interface Filters {

    enum class Character : Filters {
        NAME, STATUS, SPECIES, TYPE, GENDER
    }

    enum class Location : Filters {
        NAME, TYPE, DIMENSION
    }

    enum class Episode : Filters {
        NAME, EPISODE
    }
}