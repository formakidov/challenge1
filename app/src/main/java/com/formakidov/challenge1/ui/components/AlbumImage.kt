package com.formakidov.challenge1.ui.components

import android.R.attr.rotation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.formakidov.challenge1.ui.theme.ArrowIconBackground

@Composable
fun AlbumImage(
    url: String,
    modifier: Modifier = Modifier,
    cardRadius: Dp = 12.dp,
    imageRadius: Dp = 12.dp,
    imagePadding: Dp = 0.dp,
    rotation: Float = 0f,
    elevation: Dp = 0.dp
) {
    Surface(
        modifier = modifier
            .rotate(rotation)
            .then(if (elevation > 0.dp) Modifier.shadow(elevation, RoundedCornerShape(cardRadius)) else Modifier),
        shape = RoundedCornerShape(cardRadius),
        color = Color.White
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(imagePadding),
            contentAlignment = Alignment.Center
        ) {
            Surface(
                modifier = Modifier.fillMaxSize(),
                shape = RoundedCornerShape(imageRadius),
                color = ArrowIconBackground
            ) {
                AsyncImage(
                    model = url,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@Preview(showBackground = true, name = "List Item Style")
@Composable
fun AlbumImageListPreview() {
    MaterialTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            AlbumImage(
                url = "",
                modifier = Modifier.size(128.dp),
                cardRadius = 10.dp,
                imageRadius = 8.dp,
                imagePadding = 4.dp,
                rotation = -3f,
                elevation = 4.dp
            )
        }
    }
}

@Preview(showBackground = true, name = "Polaroid Detail Style")
@Composable
fun AlbumImagePolaroidPreview() {
    MaterialTheme {
        Box(modifier = Modifier.padding(32.dp)) {
            AlbumImage(
                url = "",
                modifier = Modifier.size(280.dp),
                cardRadius = 16.dp,
                imageRadius = 12.dp,
                imagePadding = 12.dp,
                rotation = -3f,
                elevation = 12.dp
            )
        }
    }
}
