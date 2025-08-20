package dev.dprice.pokemon.pokedex.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.dprice.pokemon.pokedex.data.MockPokemonRepository
import dev.dprice.pokemon.pokedex.data.PokemonRepository

@Module
@InstallIn(SingletonComponent::class)
interface PokemonModule {

    @Binds
    fun bindPokemonRepository(impl: MockPokemonRepository): PokemonRepository
}