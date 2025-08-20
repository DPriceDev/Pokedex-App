package dev.dprice.pokemon.pokedex

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.dprice.pokemon.pokedex.feature.detail.PokemonDetails
import dev.dprice.pokemon.pokedex.feature.list.PokemonList
import kotlinx.serialization.Serializable

object NavGraph {
    @Serializable
    data object PokemonList
    @Serializable
    data object PokemonDetails
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
            PokemonList(
                onPokemonClick = { /* todo: pass to viewmodel */ }
            )
        }
        composable<NavGraph.PokemonDetails> { PokemonDetails() }
    }
}