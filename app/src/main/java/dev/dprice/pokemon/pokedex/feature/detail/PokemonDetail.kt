package dev.dprice.pokemon.pokedex.feature.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonDetailScreen(
    // todo: add loading state
    name: String,
    imageUrl: String,
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
) {
    Scaffold(
        modifier = modifier,
        topBar = { TopBar(onBack = onBack) },
        contentWindowInsets = TopAppBarDefaults.windowInsets,
    ) { innerPadding ->
        PokemonDetail(
            name = name,
            imageUrl = imageUrl,
            modifier = Modifier.padding(innerPadding),
        )
    }
}

@Composable
private fun PokemonDetail(
    name: String,
    imageUrl: String,
    modifier: Modifier = Modifier,
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
            url = imageUrl,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Text(
            text = name,
            style = MaterialTheme.typography.titleLarge
        )

        Stats(

        )
    }
}

@Composable
fun PokemonImage(
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
                Text(text = "Error loading image")
            }
        },
        contentDescription = "image of $name",
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .widthIn(max = 256.dp)
            .background(Color.Gray), // todo: change colour and update shape
        contentScale = ContentScale.FillWidth,
    )
}

@Composable
private fun Stats(
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
        ) {
            Row {
                Text("Weight")
                Spacer(Modifier.weight(1f))
                Text("45kg")
            }
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