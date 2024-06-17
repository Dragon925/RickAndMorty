package com.github.dragon925.rickandmorty.ui.utils

import com.github.dragon925.rickandmorty.domain.utils.FilterBuilder

fun interface OnFilterApply<T: Enum<T>> {
    fun apply(filter: FilterBuilder<T>)
}