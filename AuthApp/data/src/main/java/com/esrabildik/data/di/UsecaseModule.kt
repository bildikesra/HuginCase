package com.esrabildik.data.di

import com.esrabildik.domain.repository.auth.AuthRepository
import com.esrabildik.domain.usecase.auth.LoginUseCase
import com.esrabildik.domain.usecase.auth.SignInUseCase
import com.esrabildik.domain.usecase.auth.SignUpUsecase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object UsecaseModule {

    @Provides
    fun providesSignInUseCase(repository: AuthRepository): SignInUseCase{
        return SignInUseCase(repository)
    }

    @Provides
    fun providesSignUpUseCase(repository: AuthRepository) : SignUpUsecase {
        return SignUpUsecase(repository)
    }

    @Provides
    fun providesLoginUseCase(repository: AuthRepository) : LoginUseCase {
        return LoginUseCase(repository)
    }

}