package com.bicyo.bicyo.ui.components

import android.os.Build
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import com.bicyo.bicyo.R

@Composable
fun ImageFromUrl(url:String){
    val imgLoader = ImageLoader.Builder(LocalContext.current)
        .components {
            if (Build.VERSION.SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()

    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(url)
            .crossfade(true)
            .build(),
        placeholder = rememberAsyncImagePainter(
            model = R.drawable.spinner,
            imageLoader = imgLoader
        ),
        error = rememberAsyncImagePainter(
            model = R.drawable.spinner,
            imageLoader = imgLoader
        ),
        fallback = rememberAsyncImagePainter(
            model = R.drawable.spinner,
            imageLoader = imgLoader
        ),
        contentDescription = "hola",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .clip(CircleShape)
            .aspectRatio(1f)
    )
}