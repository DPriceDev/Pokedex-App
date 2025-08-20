package dev.dprice.pokemon.pokedex.feature.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.dprice.pokemon.pokedex.NavGraph
import dev.dprice.pokemon.pokedex.data.Pokemon
import dev.dprice.pokemon.pokedex.data.PokemonRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    private val pokemonRepository: PokemonRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    val name = savedStateHandle.toRoute<NavGraph.PokemonDetails>().name

    private var detailState = MutableStateFlow<DetailState>(DetailState.Loading)
    val details = detailState.asStateFlow()

    init {
        viewModelScope.launch {
            load()
        }
    }

    fun refresh() {
        viewModelScope.launch {
            detailState.update { DetailState.Loading }
            load()
        }
    }

    private suspend fun load() {
        val result = pokemonRepository.getPokemon(name)
        detailState.update { result?.toDetails() ?: DetailState.Error }
    }
}

fun Pokemon.toDetails() = DetailState.Data(
    imageUrl = imageUrl,
    stats = stats,
)