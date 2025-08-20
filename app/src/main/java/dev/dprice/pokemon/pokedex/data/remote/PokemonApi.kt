package dev.dprice.pokemon.pokedex.data.remote

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonIgnoreUnknownKeys
import kotlinx.serialization.json.JsonNames
import retrofit2.http.GET
import retrofit2.http.Path

@Serializable
@JsonIgnoreUnknownKeys
data class PokemonResponse(
    val weight: Int,
    val sprites: Sprites,
) {
    @Serializable
    @JsonIgnoreUnknownKeys
    data class Sprites(
        @JsonNames("front_default")
        val frontDefault: String,
    )
}

interface PokemonApi {

    @GET("pokemon/{name}")
    suspend fun getPokemon(@Path("name") name: String): PokemonResponse
}