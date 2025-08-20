package dev.dprice.pokemon.pokedex.data

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

data class PokemonSummary(
    val name: String,
)

data class Pokemon(
    val imageUrl: String,
    val stats: Stats,
) {
    data class Stats(
        val weight: Int,
    )
}

interface PokemonRepository {
    fun getPokemonSummaries(): Flow<PagingData<PokemonSummary>>

    suspend fun getPokemon(name: String): Pokemon?
}