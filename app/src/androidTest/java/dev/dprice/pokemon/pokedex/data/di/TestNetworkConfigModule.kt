package dev.dprice.pokemon.pokedex.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Named

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [NetworkConfigModule::class]
)
object TestNetworkConfigModule {

    @Provides
    @Named("baseUrl")
    fun provideBaseUrl() = "http://localhost:8080/"
}