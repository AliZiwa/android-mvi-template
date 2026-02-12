package com.template.app.di

import com.template.app.data.repository.AuthRepositoryImpl
import com.template.app.data.repository.FriendRepositoryImpl
import com.template.app.data.repository.UserRepositoryImpl
import com.template.app.domain.repository.AuthRepository
import com.template.app.domain.repository.FriendRepository
import com.template.app.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    abstract fun bindUserRepository(impl: UserRepositoryImpl): UserRepository

    @Binds
    @Singleton
    abstract fun bindFriendRepository(impl: FriendRepositoryImpl): FriendRepository
}
