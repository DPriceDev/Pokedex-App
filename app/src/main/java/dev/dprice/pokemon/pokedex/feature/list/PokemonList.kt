package dev.dprice.pokemon.pokedex.feature.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewDynamicColors
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import dev.dprice.pokemon.pokedex.R
import dev.dprice.pokemon.pokedex.data.PokemonSummary
import dev.dprice.pokemon.pokedex.ui.theme.PokedexTheme
import kotlinx.coroutines.flow.flowOf
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonListScreen(
    pokemon: LazyPagingItems<PokemonSummary>,
    modifier: Modifier = Modifier,
    onPokemonClick: (String) -> Unit,
) {
    val scrollBehaviour = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier,
        topBar = { TopBar(scrollBehaviour) },
        contentWindowInsets = TopAppBarDefaults.windowInsets,
    ) { innerPadding ->
        PokemonList(
            pokemon,
            modifier = Modifier
                .padding(innerPadding)
                .nestedScroll(scrollBehaviour.nestedScrollConnection),
            onPokemonClick = onPokemonClick,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(
    scrollBehaviour: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        title = { Text(text = stringResource(R.string.pokemon)) },
        scrollBehavior = scrollBehaviour,
        modifier = modifier,
    )
}

@Composable
private fun PokemonList(
    pokemon: LazyPagingItems<PokemonSummary>,
    modifier: Modifier = Modifier,
    onPokemonClick: (String) -> Unit,
) {
    if (pokemon.loadState.hasError) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(text = stringResource(R.string.error_loading_pokemon))
            Button(
                onClick = { pokemon.retry() }
            ) {
                Text(stringResource(R.string.retry))
            }
        }
    } else {
        LazyColumn(
            contentPadding = WindowInsets.navigationBars.asPaddingValues(),
            modifier = modifier,
        ) {
            items(pokemon.itemCount) { index ->
                val item = pokemon[index]?.name ?: return@items

                PokemonRow(
                    name = item,
                    onClick = { onPokemonClick(item) },
                )

                if (index != pokemon.itemCount - 1) {
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        color = DividerDefaults.color.copy(alpha = 0.5f),
                    )
                }
            }
        }
    }
}

@Composable
private fun PokemonRow(
    name: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    val capitalizedName = name.replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
    }
    ListItem(
        headlineContent = { Text(text = capitalizedName) },
        trailingContent = {
            Icon(
                Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                contentDescription = null,
            )
        },
        modifier = Modifier
            .clickable(onClick = onClick)
            .then(modifier),
    )
}

@PreviewLightDark
@PreviewFontScale
@PreviewDynamicColors
@Composable
private fun PreviewPokemonList() {
    val pokemon = listOf(
        PokemonSummary("Bulbasaur"),
        PokemonSummary("Ivysaur"),
        PokemonSummary("Venusaur"),
    )
    val lazyPokemon = flowOf(PagingData.from(pokemon)).collectAsLazyPagingItems()
    PokedexTheme {
        PokemonListScreen(
            pokemon = lazyPokemon,
            onPokemonClick = { /* no-op for preview */ },
        )
    }
}