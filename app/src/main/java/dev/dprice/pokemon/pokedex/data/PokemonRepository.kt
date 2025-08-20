package dev.dprice.pokemon.pokedex.data

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
    suspend fun getPokemonSummaries(): List<PokemonSummary>

    suspend fun getPokemon(name: String): Pokemon?
}