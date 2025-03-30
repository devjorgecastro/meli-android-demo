package com.example.meli.core.common.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ScopeMainNoInmediate

@Module
@InstallIn(ViewModelComponent::class)
class DispatcherModule {

    @ScopeMainNoInmediate
    @ViewModelScoped
    @Provides
    fun provideScopeNoInmediate() = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    @ViewModelScoped
    @Provides
    fun provideScope() = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
}
