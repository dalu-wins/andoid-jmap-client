package de.dalu_wins.androidjmapclient.features.email.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import de.dalu_wins.androidjmapclient.features.email.data.MockEmailRepository
import de.dalu_wins.androidjmapclient.features.email.domain.repository.EmailRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object EmailModule {

    @Provides
    @Singleton
    fun provideEmailRepository(): EmailRepository {
        return MockEmailRepository()
    }
}
