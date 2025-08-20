package dev.dprice.pokemon.pokedex.feature.detail

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import dev.dprice.pokemon.pokedex.R
import dev.dprice.pokemon.pokedex.data.Pokemon

sealed class DetailState {
    data class Data(
        val imageUrl: String,
        val stats: Pokemon.Stats,
    ) : DetailState()
    data object Loading : DetailState()
    data object Error : DetailState()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonDetailScreen(
    name: String,
    details: DetailState,
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    onRetry: () -> Unit,
) {
    Scaffold(
        modifier = modifier,
        topBar = { TopBar(onBack = onBack) },
        contentWindowInsets = TopAppBarDefaults.windowInsets,
    ) { innerPadding ->
        PokemonDetail(
            name = name,
            details = details,
            modifier = Modifier.padding(innerPadding),
            onRetry = onRetry,
        )
    }
}

@Composable
private fun PokemonDetail(
    name: String,
    details: DetailState,
    modifier: Modifier = Modifier,
    onRetry: () -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .then(modifier),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        PokemonImage(
            name = name,
            details = details,
            modifier = Modifier.padding(horizontal = 16.dp),
            onRetry = onRetry,
        )

        Text(
            text = name,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.testTag("detail_title"),
        )

        StatList(details, onRetry = onRetry)
    }
}

@Composable
private fun PokemonImage(
    name: String,
    details: DetailState,
    modifier: Modifier = Modifier,
    onRetry: () -> Unit,
) {
   Card(
       modifier = modifier,
   ) {
       Box(
           contentAlignment = Alignment.Center,
           modifier = Modifier
               .padding(16.dp)
               .size(256.dp)
       ) {
           AnimatedContent(
               targetState = details,
               contentAlignment = Alignment.Center,
           ) { targetState ->
               when (targetState) {
                   is DetailState.Data -> PokemonImage(
                       name = name,
                       url = targetState.imageUrl,
                       modifier = Modifier.fillMaxWidth(),
                   )

                   DetailState.Error -> FetchingError(onRetry = onRetry)
                   DetailState.Loading -> CircularProgressIndicator()
               }
           }
       }
   }
}

@Composable
private fun PokemonImage(
    name: String,
    url: String,
    modifier: Modifier = Modifier,
) {
    SubcomposeAsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(url)
            .crossfade(true)
            .build(),
        loading = {
            Box(
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator()
            }
        },
        error = {
            Box(
                contentAlignment = Alignment.Center,
            ) {
                Text(text = stringResource(R.string.error_loading_image))
            }
        },
        contentDescription = stringResource(R.string.image_of, name),
        modifier = modifier,
        contentScale = ContentScale.FillWidth,
    )
}

@Composable
private fun StatList(
    details: DetailState,
    modifier: Modifier = Modifier,
    onRetry: () -> Unit,
) {
    Card(
        modifier = modifier,
    ) {
        AnimatedContent(
            targetState = details,
            contentAlignment = Alignment.Center,
        ) { targetState ->
            when (targetState) {
                is DetailState.Data -> Stats(
                    stats = targetState.stats,
                    modifier = Modifier.padding(16.dp),
                )

                DetailState.Error -> FetchingError(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    onRetry = onRetry,
                )

                DetailState.Loading -> Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

@Composable
fun Stats(
    stats: Pokemon.Stats,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row {
            Text("Weight")
            Spacer(Modifier.weight(1f))

            val weight = stats.weight.toDouble() / 10f
            Text("$weight kg")
        }
    }
}

@Composable
private fun FetchingError(
    modifier: Modifier = Modifier,
    onRetry: () -> Unit,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            stringResource(R.string.could_not_fetch_details),
            fontStyle = FontStyle.Italic,
        )
        Button(onClick = onRetry) {
            Text(stringResource(R.string.retry))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
) {
    TopAppBar(
        title = { /* pokemon name presented in list */ },
        navigationIcon = { 
            IconButton(onClick = onBack) {
                Icon(
                    Icons.AutoMirrored.Rounded.ArrowBack,
                    contentDescription = null,
                )
            }
        },
        modifier = modifier,
    )
}