package com.yz3ro.firebasesgnup.di

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.yz3ro.firebasesgnup.data.datasource.AuthDataSource
import com.yz3ro.firebasesgnup.data.repo.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideAuthDataSource(collectionAuth : CollectionReference) : AuthDataSource {
        return provideAuthDataSource(collectionAuth)
    }
    @Provides
    @Singleton
    fun provideAuthRepository(ads : AuthDataSource) : AuthRepository {
        return AuthRepository(ads)
    }
    @Provides
    @Singleton
    fun provideCollectionReference() : CollectionReference {
        return Firebase.firestore.collection("users")
    }

}