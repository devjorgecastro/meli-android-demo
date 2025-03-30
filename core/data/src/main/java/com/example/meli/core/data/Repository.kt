package com.example.meli.core.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

open class Repository(
    val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    protected suspend fun <T> launch(block: suspend CoroutineScope.() -> T): T {
        return withContext(dispatcher) {
            block()
        }
    }
}