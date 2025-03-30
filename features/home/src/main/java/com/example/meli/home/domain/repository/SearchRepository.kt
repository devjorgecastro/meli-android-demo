package com.example.meli.home.domain.repository

import com.example.meli.home.domain.entity.QueryResult

interface SearchRepository {
    suspend fun getSuggestedQueries(
        siteId: String,
        showFilters: Boolean = false,
        limit: Int,
        apiVersion: Int = 2,//SUGGESTED_QUERIES_API_VERSION,
        query: String
    ): QueryResult
}
