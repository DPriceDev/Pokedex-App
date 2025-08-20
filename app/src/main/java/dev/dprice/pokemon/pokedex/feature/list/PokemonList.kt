package dev.dprice.pokemon.pokedex.feature.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.PreviewDynamicColors
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import dev.dprice.pokemon.pokedex.ui.theme.PokedexTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonList(
    modifier: Modifier = Modifier,
    onPokemonClick: (String) -> Unit,
) {
    val scrollBehaviour = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier,
        topBar = { TopBar(scrollBehaviour) },
        contentWindowInsets = TopAppBarDefaults.windowInsets,
    ) { innerPadding ->
        LazyColumn(
            contentPadding = WindowInsets.navigationBars.asPaddingValues(),
            modifier = Modifier
                .padding(innerPadding)
                .nestedScroll(scrollBehaviour.nestedScrollConnection),
        ) {
            val count = 100
            items(count) {
                PokemonRow(
                    name = "Pokemon $it",
                    onClick = { onPokemonClick("Pokemon $it") }, //todo: pull pokemon name
                )

                if (it != count - 1) {
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(
    scrollBehaviour: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier,
) {
    // todo: pokeball leading icon
    TopAppBar(
        title = { Text(text = "Pokemon") },
        scrollBehavior = scrollBehaviour,
        modifier = modifier,
    )
}

@Composable
private fun PokemonRow(
    name: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    ListItem(
        headlineContent = { Text(text = name) },
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
    PokedexTheme {
        PokemonList(
            onPokemonClick = { /* no-op for preview */ },
        )
    }
}