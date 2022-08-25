package com.example.oic.di


import com.example.oic.data.repo.FirebaseRepository
import com.example.oic.data.repo.FirebaseRepositoryImpl
import com.example.oic.data.repo.SearchWordRepository
import com.example.oic.data.repo.SearchWordRepositoryImpl
import com.example.oic.data.source.remote.FirebaseRemoteDataSource
import com.example.oic.data.source.remote.FirebaseRemoteDataSourceImpl
import com.example.oic.data.source.remote.SearchWordRemoteDataSource
import com.example.oic.data.source.remote.SearchWordRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindFirebaseRepository(firebaseRepositoryImpl: FirebaseRepositoryImpl): FirebaseRepository

    @Singleton
    @Binds
    abstract fun bindFirebaseRemoteDataSource(firebaseRemoteDataSourceImpl: FirebaseRemoteDataSourceImpl): FirebaseRemoteDataSource


    @Singleton
    @Binds
    abstract fun bindSearchWordRepository(searchWordRepositoryImpl: SearchWordRepositoryImpl): SearchWordRepository

    @Singleton
    @Binds
    abstract fun bindSearchWordRemoteDataSource(searchWordRemoteDataSourceImpl: SearchWordRemoteDataSourceImpl): SearchWordRemoteDataSource

}

