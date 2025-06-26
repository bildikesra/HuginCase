package com.esrabildik.data.di

import com.esrabildik.data.repository.AuthRepositoryImpl
import com.esrabildik.domain.repository.auth.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataModule {


    @Provides
    fun provideFirebaseAuth() : FirebaseAuth {
       return  FirebaseAuth.getInstance()
    }

    @Provides
    fun provideAuthRepository(firebaseAuth: FirebaseAuth)
    : AuthRepository {
        return AuthRepositoryImpl(firebaseAuth)
    }



}