package com.github.dragon925.rickandmorty.ui.utils

import com.github.dragon925.rickandmorty.domain.models.Character
import com.github.dragon925.rickandmorty.domain.models.Episode
import com.github.dragon925.rickandmorty.domain.models.Location
import com.github.dragon925.rickandmorty.ui.models.CharacterItem
import com.github.dragon925.rickandmorty.ui.models.EpisodeItem
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