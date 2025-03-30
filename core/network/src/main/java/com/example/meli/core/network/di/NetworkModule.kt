package com.example.meli.core.network.di

import com.example.meli.core.network.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Qualifier
import javax.inject.Singleton

const val BASE_URL = "https://api.mercadolibre.com/"
const val BASE_SUGGESTED_QUERIES_URL = "https://http2.mlstatic.com/"

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class SuggestedQueriesClient

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideMercadoLibreHttpClient(): HttpClient {
        return provideHttpClient(BASE_URL)
    }

    @Provides
    @Singleton
    @SuggestedQueriesClient
    fun provideSuggestedQueriesHttpClient(): HttpClient {
        return provideHttpClient(BASE_SUGGESTED_QUERIES_URL)
    }

    private fun provideHttpClient(baseUrl: String): HttpClient {
        return HttpClient(Android) {
            defaultRequest {
                url(baseUrl)
            }
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
            engine {
                connectTimeout = 100_000
                socketTimeout = 100_000
            }
            if (BuildConfig.DEBUG) {
                install(Logging) {
                    logger = Logger.DEFAULT
                    level = LogLevel.BODY
                }
            }
        }
    }
}