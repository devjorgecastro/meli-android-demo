package com.example.meli.home.domain.usecase

import com.example.meli.home.domain.entity.QueryResult
import com.example.meli.home.domain.repository.SearchRepository
import javax.inject.Inject

class GetSuggestedQueriesUseCase @Inject constructor(
    private val repository: SearchRepository
) {
    suspend operator fun invoke(
        siteId: String,
        showFilters: Boolean = false,
        limit: Int = 50,
        query: String,
    ): QueryResult {
        return repository.getSuggestedQueries(
            siteId = siteId,
            showFilters = showFilters,
            limit = limit,
            query = query
        )
    }
}
