package com.example.meli.home.data.repository

import com.example.meli.core.data.Repository
import com.example.meli.core.network.di.SuggestedQueriesClient
import com.example.meli.home.data.dto.QueryResultDto
import com.example.meli.home.domain.entity.QueryResult
import com.example.meli.home.domain.repository.SearchRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.appendPathSegments
import javax.inject.Inject

class DefaultSearchRepository @Inject constructor(
    @SuggestedQueriesClient val httpClient: HttpClient,
): Repository(), SearchRepository {
    override suspend fun getSuggestedQueries(
        siteId: String,
        showFilters: Boolean,
        limit: Int,
        apiVersion: Int,
        query: String
    ): QueryResult {
        return launch {
            httpClient.get {
                url {
                    appendPathSegments("resources", "sites", siteId, "autosuggest")
                    parameters.append("showFilters", showFilters.toString())
                    parameters.append("limit", limit.toString())
                    parameters.append("api_version", apiVersion.toString())
                    parameters.append("q", query)
                }
            }.body<QueryResultDto>().asQueryResultEntity()
        }
    }
}
