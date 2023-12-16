package com.example.spsassignment.di.repository

import com.example.spsassignment.repository.SearchRepository
import com.example.spsassignment.repository.SearchRepositoryImpl
import com.example.spsassignment.repository.TvRepository
import com.example.spsassignment.repository.TvRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun provideSearchRepository(implements: SearchRepositoryImpl): SearchRepository

    @Binds
    fun provideTvRepository(implements: TvRepositoryImpl): TvRepository

}