package dev.dprice.pokemon.pokedex

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.dprice.pokemon.pokedex.feature.detail.PokemonDetailScreen
import dev.dprice.pokemon.pokedex.feature.detail.PokemonDetailViewModel
import dev.dprice.pokemon.pokedex.feature.list.PokemonListScreen
import dev.dprice.pokemon.pokedex.feature.list.PokemonListViewModel
import kotlinx.serialization.Serializable

object NavGraph {
    @Serializable
    data object PokemonList
    @Serializable
    data class PokemonDetails(
        val name: String,
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

            PokemonListScreen(
                pokemon = viewModel.pokemon,
                onPokemonClick = { navController.navigate(NavGraph.PokemonDetails(it)) }
            )
        }
        composable<NavGraph.PokemonDetails> {
            val viewModel: PokemonDetailViewModel = hiltViewModel()
            val details by viewModel.details.collectAsStateWithLifecycle()

            PokemonDetailScreen(
                name = viewModel.name,
                details = details,
                onBack = { navController.navigateUp() },
                onRetry = { viewModel.refresh() },
            )
        }
    }
}