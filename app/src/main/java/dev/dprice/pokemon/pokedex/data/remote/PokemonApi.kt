package dev.dprice.pokemon.pokedex.data.remote

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonIgnoreUnknownKeys
import kotlinx.serialization.json.JsonNames
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

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

@Serializable
@JsonIgnoreUnknownKeys
data class Paged<T>(
    val results: List<T>,
)

@Serializable
@JsonIgnoreUnknownKeys
data class PokemonSummaryResponse(
    val name: String,
)

interface PokemonApi {

    @GET("pokemon")
    suspend fun getPokemonSummaries(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int,
    ): Paged<PokemonSummaryResponse>

    @GET("pokemon/{name}")
    suspend fun getPokemon(@Path("name") name: String): PokemonResponse
}