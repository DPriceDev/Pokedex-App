package dev.dprice.pokemon.pokedex.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object NetworkConfigModule {

    @Provides
    @Named("baseUrl")
    fun provideBaseUrl() = "https://pokeapi.co/api/v2/"
}