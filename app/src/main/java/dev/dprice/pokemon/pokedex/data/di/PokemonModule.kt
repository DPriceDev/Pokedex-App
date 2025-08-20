package dev.dprice.pokemon.pokedex.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.dprice.pokemon.pokedex.data.PokemonRepository
import dev.dprice.pokemon.pokedex.data.RemotePokemonRepository

@Module
@InstallIn(SingletonComponent::class)
interface PokemonModule {

    @Binds
    fun bindPokemonRepository(impl: RemotePokemonRepository): PokemonRepository
}