package dev.dprice.pokemon.pokedex.data

import dev.dprice.pokemon.pokedex.data.mapper.toPokemon
import dev.dprice.pokemon.pokedex.data.remote.PokemonApi
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

class RemotePokemonRepository @Inject constructor(
    private val pokemonApi: PokemonApi,
): PokemonRepository {
    override suspend fun getPokemonSummaries(): List<PokemonSummary> {
        return listOf(
            PokemonSummary("Bulbasaur"),
            PokemonSummary("Ivysaur"),
            PokemonSummary("Venusaur"),
        )
    }

    override suspend fun getPokemon(name: String): Pokemon? {
        return try {
            pokemonApi.getPokemon(name).toPokemon()
        } catch (error: IOException) {
            Timber.e(error)
            null
        } catch (error: HttpException) {
            Timber.e(error)
            null
        }
    }
}