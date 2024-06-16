package com.github.dragon925.rickandmorty.ui.utils

import com.github.dragon925.rickandmorty.domain.models.Character
import com.github.dragon925.rickandmorty.domain.models.Episode
import com.github.dragon925.rickandmorty.domain.models.Location
import com.github.dragon925.rickandmorty.domain.state.DataState
import com.github.dragon925.rickandmorty.ui.models.CharacterItem
import com.github.dragon925.rickandmorty.ui.models.CharacterShortItem
import com.github.dragon925.rickandmorty.ui.models.EpisodeItem
import com.github.dragon925.rickandmorty.ui.models.EpisodeShortItem
import com.github.dragon925.rickandmorty.ui.models.LocationItem

fun Character.toItem() = CharacterItem(
    id,
    name,
    status,
    species,
    gender,
    origin.name,
    location.name,
    image,
    episodIds.size
)

fun Episode.toItem() = EpisodeItem(
    id,
    name,
    airDate,
    episode,
    characterIds.size
)

fun Location.toItem() = LocationItem(
    id,
    name,
    type,
    dimension,
    residentIds.size
)

fun Character.toShortItem() = CharacterShortItem(id, name, status, species, gender, image)

fun Episode.toShortItem() = EpisodeShortItem(id, name, airDate, episode)

inline fun <T, E, R> DataState<T, E>.map(transform: (T) -> R): DataState<R, E> {
    return when(this) {
        DataState.Loading -> DataState.Loading
        is DataState.Error -> DataState.Error(this.error)
        is DataState.Loaded -> DataState.Loaded(transform(this.data))
    }
}