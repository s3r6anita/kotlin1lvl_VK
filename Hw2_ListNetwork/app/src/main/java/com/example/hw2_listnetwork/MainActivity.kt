package com.example.hw2_listnetwork

import android.graphics.drawable.Icon
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.AsyncImagePainter.State.Empty.painter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.example.hw2_listnetwork.data.Datasource
import com.example.hw2_listnetwork.ui.theme.Hw2_ListNetworkTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Hw2_ListNetworkTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
//                    SetImages(
//                        images = Datasource().loadImages()
//                    )
                    downloadImages(
                        urls = Datasource().getImagesURLs()
                    )
                }
            }
        }
    }
}

@Composable
fun downloadImages(urls: List<String>, modifier: Modifier = Modifier) {
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
    ) {
        items(urls) {url ->
            imageCard(url)
        }
    }
}

@Composable
fun imageCard(url: String, modifier: Modifier = Modifier){
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        modifier = modifier
            .padding(horizontal = 10.dp, vertical = 5.dp)
    ) {
        downloadImage(url)
    }
}

@Composable
fun downloadImage(url: String) {

    var retryHash by remember { mutableStateOf(0) }

    SubcomposeAsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(url)
            .setParameter("retry_hash", retryHash, memoryCacheKey = null)
            .crossfade(true)
            .build(),
        loading = {
            CircularProgressIndicator(modifier = Modifier.padding(12.dp).size(24.dp))
        },
        error = {
            IconButton(
                onClick = { retryHash++ }
            ) {
                androidx.compose.material3.Icon(
                    painter = painterResource(id = R.drawable.ic_refresh),
                    contentDescription = "refresh"
                )
            }
        },
        contentScale = ContentScale.Crop,
        contentDescription = null
    )
}



//            placeholder = painterResource(id = R.drawable.image1),
//            error = painterResource(id = R.drawable.image12),

//@Composable
//fun InternetImageCard(url: String, modifier: Modifier = Modifier){
//    Card(
//        elevation = CardDefaults.cardElevation(
//            defaultElevation = 10.dp
//        ),
//        modifier = modifier
//            .padding(horizontal = 10.dp, vertical = 5.dp)
//    ) {
//        val painter = rememberAsyncImagePainter(
//            model = ImageRequest.Builder(LocalContext.current)
//                .data("https://example.com/image.jpg")
//                .size(Size.ORIGINAL) // Set the target size to load the image at.
//                .build()
//        )
//
//        if (painter.state is AsyncImagePainter.State.Loading) {
//            // This will be executed during the first composition if the image is in the memory cache.
//            CircularProgressIndicator(modifier = Modifier.size(30.dp))
//        }
//
//        Image(
//            painter = painter,
//            contentDescription = null
//        )
//    }
//}
