package com.example.hw2_listnetwork.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.hw2_listnetwork.R
import com.example.hw2_listnetwork.model.Photo

@Composable
fun SetScreen(uiState: UiState, retryAction: () -> Unit, modifier: Modifier = Modifier) {

    when (uiState) {
        is UiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())

        is UiState.Success -> ResultScreen(uiState.photos, modifier = modifier.fillMaxWidth())

        is UiState.Error -> ErrorScreen(retryAction, uiState.e, modifier = modifier.fillMaxSize())
    }
}


@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .padding(24.dp)
                .size(48.dp)
        )
    }
}

@Composable
fun ErrorScreen(retryAction: () -> Unit, e: Exception, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(
            modifier = Modifier.size(64.dp),
            onClick = { retryAction() }
        ) {
            Icon(
                modifier = Modifier.size(64.dp),
                painter = painterResource(id = R.drawable.ic_refresh),
                contentDescription = "refresh"
            )
        }
        Text(text = "Error: $e", modifier = Modifier.padding(10.dp))
    }
}

@Composable
fun ResultScreen(photos: List<Photo>, modifier: Modifier = Modifier) {
    downloadImagesCards(photos, modifier)
}

@Composable
fun downloadImagesCards(photos: List<Photo>, modifier: Modifier = Modifier) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Adaptive(150.dp),
        verticalItemSpacing = 4.dp,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        content = {
            items(photos) { photo ->
                imageCard(photo)
            }
        },
        modifier = Modifier.fillMaxSize()
    )
//    /** first realization by one column */
//    LazyColumn(
//        horizontalAlignment = Alignment.CenterHorizontally,
//        modifier = modifier
//    ) {
//        items(photos) { photo ->
//            imageCard(photo)
//        }
//    }
}

@Composable
fun imageCard(photo: Photo, modifier: Modifier = Modifier) {
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        shape = MaterialTheme.shapes.medium,
        modifier = modifier
//            .padding(horizontal = 10.dp, vertical = 5.dp)
    ) {
        downloadImage(photo)
    }
}

@Composable
fun downloadImage(photo: Photo) {

    var retryHash by remember { mutableStateOf(0) }

    SubcomposeAsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(photo.imgSrc)
            .setParameter("retry_hash", retryHash, memoryCacheKey = null)
            .crossfade(true)
            .build(),
        loading = {
            CircularProgressIndicator(
                modifier = Modifier
                    .padding(12.dp)
                    .size(24.dp)
            )
        },
        error = {
            IconButton(
                onClick = { retryHash++ }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_refresh),
                    contentDescription = "refresh"
                )
            }
        },
        contentScale = ContentScale.Crop,
        contentDescription = null
    )
}
