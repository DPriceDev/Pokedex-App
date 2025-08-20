package dev.dprice.pokemon.pokedex.data.mapper

import dev.dprice.pokemon.pokedex.data.Pokemon
import dev.dprice.pokemon.pokedex.data.remote.PokemonResponse

fun PokemonResponse.toPokemon() = Pokemon(
    imageUrl = sprites.frontDefault,
    stats = Pokemon.Stats(
        weight = weight,
    )
)