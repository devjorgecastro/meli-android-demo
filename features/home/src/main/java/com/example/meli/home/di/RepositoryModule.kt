package com.example.meli.home.di

import com.example.meli.home.data.repository.DefaultSearchRepository
import com.example.meli.home.domain.repository.SearchRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    @ViewModelScoped
    abstract fun provideSearchRepository(repository: DefaultSearchRepository): SearchRepository
}
