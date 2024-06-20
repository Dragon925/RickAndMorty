package com.github.dragon925.rickandmorty.data.sources

import com.github.dragon925.rickandmorty.data.sources.network.api.Api
import com.github.dragon925.rickandmorty.data.sources.network.dto.Convertable
import com.github.dragon925.rickandmorty.data.utils.Result
import com.github.dragon925.rickandmorty.domain.errors.Error
import com.github.dragon925.rickandmorty.domain.models.Model
import com.github.dragon925.rickandmorty.domain.models.Page
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeoutOrNull
import retrofit2.Response
import kotlin.math.min

class RemoteSource<T: Model, K: Convertable<T>>(
    private val api: Api<T, K>
) : DataSource<T> {

    companion object {
        private const val TIMEOUT = 30000L
        private const val PART_SIZE = 20
    }

    override suspend fun loadAll(
        page: Int?,
        filters: Map<String, String>
    ): Result<Page<T>, Error> = try {
        withContext(Dispatchers.IO) {
            withTimeoutOrNull(TIMEOUT) {
                async {  api.loadAll(page, filters) }.await()
            }
        }?.let { response ->
            if (!response.isSuccessful) {
                Result.Error(codeToError(response.code()))
            } else {
                response.body()?.let { body ->
                    val info = body.info
                    val list = body.results.map { it.toModel() }
                    Result.Success(Page(
                        total = info.pages,
                        current = page ?: 1,
                        hasNext = !info.next.isNullOrBlank(),
                        hasPrev = !info.prev.isNullOrBlank(),
                        list = list
                    ))
                } ?: Result.Error(Error.Network.SERVER_FAILURE)
            }
        } ?:Result.Error(Error.Network.NO_CONNECTION)
    } catch (e: Exception) { Result.Error(Error.Network.UNKNOWN) }


    override suspend fun loadById(id: Int): Result<T, Error> = try {
        withContext(Dispatchers.IO) {
            withTimeoutOrNull(TIMEOUT) {
                async { api.loadById(id) }.await()
            }
        }?.let { response ->
            if (!response.isSuccessful) {
                Result.Error(codeToError(response.code()))
            } else {
                response.body()?.let { body ->
                    Result.Success(body.toModel())
                } ?: Result.Error(Error.Network.SERVER_FAILURE)
            }
        } ?: Result.Error(Error.Network.NO_CONNECTION)
    } catch (e: Exception) { Result.Error(Error.Network.UNKNOWN) }

    override suspend fun loadByIds(ids: List<Int>): Result<List<T>, Error> = try {
        if (ids.size == 1) {
            when (val result = loadById(ids.first())) {
                is Result.Error -> Result.Error(result.error)
                is Result.Success -> Result.Success(listOf(result.result))
            }
        } else {
            withContext(Dispatchers.IO) {
                withTimeoutOrNull(TIMEOUT) {
                    val requests = mutableListOf<Deferred<Response<List<K>>>>()
                    val n = (ids.size + PART_SIZE - 1) / PART_SIZE
                    for (i in 0..<n) {
                        val start = PART_SIZE * i
                        val request = async {
                            api.loadByIds(
                                ids.subList(start, min(start + PART_SIZE, ids.size))
                                    .joinToString(",")
                            )
                        }
                        requests.add(request)
                    }
                    requests.awaitAll()
                }
            }?.let { responses ->
                val unsuccess = responses.firstOrNull { !it.isSuccessful }
                if (unsuccess != null) {
                    Result.Error(codeToError(unsuccess.code()))
                } else if (responses.any { it.body() == null }) {
                    Result.Error(Error.Network.SERVER_FAILURE)
                } else {
                    Result.Success(
                        responses.flatMap { it.body()!! }.map { it.toModel() }.sortedBy { it.id }
                    )
                }
            } ?: Result.Error(Error.Network.NO_CONNECTION)
        }
    } catch (e: Exception) { Result.Error(Error.Network.UNKNOWN) }

    private fun codeToError(code: Int) = when(code) {
        404 -> Error.Network.NOT_FOUND
        in 400..<500 -> Error.Network.INCORRECT_REQUEST
        in 500..<600 -> Error.Network.SERVER_FAILURE
        else -> Error.Network.UNKNOWN
    }
}