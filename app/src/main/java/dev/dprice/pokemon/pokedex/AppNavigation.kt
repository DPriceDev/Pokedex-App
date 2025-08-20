package dev.dprice.pokemon.pokedex

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.dprice.pokemon.pokedex.feature.detail.PokemonDetails
import dev.dprice.pokemon.pokedex.feature.list.PokemonList
import dev.dprice.pokemon.pokedex.feature.list.PokemonListViewModel
import kotlinx.serialization.Serializable

object NavGraph {
    @Serializable
    data object PokemonList
    @Serializable
    data class PokemonDetails(
        val id: String,
    )
}

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = NavGraph.PokemonList,
        modifier = modifier,
    ) {
        composable<NavGraph.PokemonList> {
            val viewModel: PokemonListViewModel = hiltViewModel()

            PokemonList(
                pokemon = viewModel.pokemon,
                onPokemonClick = { navController.navigate(NavGraph.PokemonDetails(it)) }
            )
        }
        composable<NavGraph.PokemonDetails> { PokemonDetails() }
    }
}