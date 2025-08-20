package dev.dprice.pokemon.pokedex.data.mapper

import dev.dprice.pokemon.pokedex.data.Pokemon
import dev.dprice.pokemon.pokedex.data.PokemonSummary
import dev.dprice.pokemon.pokedex.data.remote.PokemonResponse
import dev.dprice.pokemon.pokedex.data.remote.PokemonSummaryResponse

fun PokemonResponse.toPokemon() = Pokemon(
    imageUrl = sprites.frontDefault,
    stats = Pokemon.Stats(
        weight = weight,
    )
)

fun PokemonSummaryResponse.toPokemonSummary() = PokemonSummary(
    name = name,
)