package com.github.dragon925.rickandmorty.domain.utils

import java.util.EnumMap

sealed interface FilterBuilder<T : Enum<T>> {

    fun build(): EnumMap<T, String>
}