package com.github.dragon925.rickandmorty.data.sources

import com.github.dragon925.rickandmorty.data.utils.Result
import com.github.dragon925.rickandmorty.domain.errors.Error
import com.github.dragon925.rickandmorty.domain.models.Page

sealed interface DataSource<T> {
    suspend fun loadAll(
        page: Int? = null,
        filters: Map<String, String> = emptyMap()
    ): Result<Page<T>, Error>

    suspend fun loadById(id: Int): Result<T, Error>

    suspend fun loadByIds(ids: List<Int>): Result<List<T>, Error>
}