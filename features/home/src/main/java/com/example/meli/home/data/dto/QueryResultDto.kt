package com.example.meli.home.data.dto

import com.example.meli.home.domain.entity.QueryResult
import com.example.meli.home.domain.entity.SuggestedQuery
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class QueryResultDto(
    @SerialName( "q") val userInputQuery: String,
    @SerialName("suggested_queries") val suggestedQueries: List<SuggestedQueryDto>
) {
    fun asQueryResultEntity() =
        QueryResult(
            userInputQuery = userInputQuery,
            queries = suggestedQueries.map { it.asSuggestedQueryEntity() }
        )
}

@Serializable
data class SuggestedQueryDto(
    @SerialName("q") val suggestedQuery: String,
    @SerialName("match_start") val matchStart: Int,
    @SerialName("match_end") val matchEnd: Int,
) {
    fun asSuggestedQueryEntity() =
        SuggestedQuery(
            description = suggestedQuery,
            matchStart = matchStart,
            matchEnd = matchEnd
        )
}
