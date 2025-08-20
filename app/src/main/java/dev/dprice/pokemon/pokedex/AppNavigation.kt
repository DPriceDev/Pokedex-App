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
        startDestination = NavGraph.PokemonList
    ) {
        composable<NavGraph.PokemonList> { PokemonList(modifier) }
        composable<NavGraph.PokemonDetails> { PokemonDetails(modifier) }
    }
}