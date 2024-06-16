package com.github.dragon925.rickandmorty.data.sources

import com.github.dragon925.rickandmorty.data.sources.network.dto.Convertable
import com.github.dragon925.rickandmorty.data.utils.Result
import com.github.dragon925.rickandmorty.domain.errors.Error
import com.github.dragon925.rickandmorty.domain.models.Model
import com.github.dragon925.rickandmorty.domain.models.Page

class SourcesHandler<T: Model>(
    private val remoteSource: RemoteSource<T, out Convertable<T>>
) : DataSource<T> {

    override suspend fun loadAll(
        page: Int?,
        filters: Map<String, String>
    ): Result<Page<T>, Error> = remoteSource.loadAll(page, filters)

    override suspend fun loadById(id: Int): Result<T, Error> = remoteSource.loadById(id)

    override suspend fun loadByIds(ids: List<Int>): Result<List<T>, Error> = remoteSource.loadByIds(ids)
}