package dev.dprice.pokemon.pokedex.feature.list

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.dprice.pokemon.pokedex.data.PokemonRepository
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val pokemonRepository: PokemonRepository
): ViewModel() {
    val pokemon = listOf("Bulbasaur", "Ivysaur", "Venusaur")
}