package com.example.meli.home.domain.entity

data class QueryResult(
    val userInputQuery: String,
    val queries: List<SuggestedQuery>
)

data class SuggestedQuery(
    val description: String,
    val matchStart: Int,
    val matchEnd: Int,
)
