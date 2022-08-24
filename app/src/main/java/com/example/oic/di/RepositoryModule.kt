package com.example.oic.di


import com.example.oic.data.repo.FirebaseRepository
import com.example.oic.data.repo.FirebaseRepositoryImpl
import com.example.oic.data.source.remote.FirebaseRemoteDataSource
import com.example.oic.data.source.remote.FirebaseRemoteDataSourceImpl
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

}

