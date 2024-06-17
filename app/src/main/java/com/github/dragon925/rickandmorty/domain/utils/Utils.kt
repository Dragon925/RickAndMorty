package com.github.dragon925.rickandmorty.domain.utils

import java.util.EnumMap

inline fun <reified E: Enum<E>, V> emptyEnumMap() = EnumMap<E, V>(E::class.java)

fun <E: Enum<E>, V, R> EnumMap<E, V>.toMap(transform: (E) -> R): Map<R, V> {
    val newMap = mutableMapOf<R, V>()
    this.forEach { key, value -> newMap.put(transform(key), value) }
    return newMap
}