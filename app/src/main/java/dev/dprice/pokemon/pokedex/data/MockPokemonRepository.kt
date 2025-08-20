package dev.dprice.pokemon.pokedex.data

import kotlinx.coroutines.delay
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

class MockPokemonRepository @Inject constructor(): PokemonRepository {
    override suspend fun getPokemonSummaries(): List<PokemonSummary> {
        return listOf(
            PokemonSummary("Bulbasaur"),
            PokemonSummary("Ivysaur"),
            PokemonSummary("Venusaur"),
        )
    }

    override suspend fun getPokemon(name: String): Pokemon? {
        delay(2.seconds)
        return Pokemon(
            name = "Bulbasaur",
            imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png",
            stats = Pokemon.Stats(
                weight = 69,
            )
        )
    }
}