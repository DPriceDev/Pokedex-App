package dev.dprice.pokemon.pokedex.feature.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.dprice.pokemon.pokedex.data.PokemonRepository
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    pokemonRepository: PokemonRepository
): ViewModel() {

    val pokemon = pokemonRepository.getPokemonSummaries()
        .cachedIn(viewModelScope)
}