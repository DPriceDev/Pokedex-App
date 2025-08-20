package dev.dprice.pokemon.pokedex

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.paging.compose.collectAsLazyPagingItems
import dev.dprice.pokemon.pokedex.feature.detail.PokemonDetailScreen
import dev.dprice.pokemon.pokedex.feature.detail.PokemonDetailViewModel
import dev.dprice.pokemon.pokedex.feature.list.PokemonListScreen
import dev.dprice.pokemon.pokedex.feature.list.PokemonListViewModel

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "pokemon/list",
        modifier = modifier,
    ) {
        composable("pokemon/list") {
            val viewModel: PokemonListViewModel = hiltViewModel()

            val pokemon = viewModel.pokemon.collectAsLazyPagingItems()

            PokemonListScreen(
                pokemon = pokemon,
                onPokemonClick = {
                    navController.navigate("pokemon/detail?name=$it")
                }
            )
        }
        composable(
            "pokemon/detail?name={name}",
            arguments = listOf(navArgument("name") { type = NavType.StringType })
        ) {
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