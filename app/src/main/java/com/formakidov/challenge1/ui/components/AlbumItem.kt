package com.formakidov.challenge1.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.formakidov.challenge1.data.model.Album
import com.formakidov.challenge1.ui.theme.AlbumItemBorderColor
import com.formakidov.challenge1.ui.theme.AlbumItemShadowColor1
import com.formakidov.challenge1.ui.theme.AlbumItemShadowColor2
import com.formakidov.challenge1.ui.theme.ArrowIconBackground
import com.formakidov.challenge1.ui.theme.SectionTabTextUnselected
import com.formakidov.challenge1.ui.theme.Typography

@Composable
fun AlbumItem(
    album: Album,
    onItemClick: (Album) -> Unit,
    onToggleSave: (Album) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = 6.dp,
                spotColor = AlbumItemShadowColor1,
                ambientColor = AlbumItemShadowColor1,
                shape = RoundedCornerShape(20.dp)
            )
            .shadow(
                elevation = 2.dp,
                spotColor = AlbumItemShadowColor2,
                ambientColor = AlbumItemShadowColor2,
                shape = RoundedCornerShape(20.dp)
            )
            .background(color = Color.White, shape = RoundedCornerShape(size = 20.dp))
            .border(
                width = 1.dp,
                color = AlbumItemBorderColor,
                shape = RoundedCornerShape(size = 20.dp)
            )
            .clickable { onItemClick(album) }
            .padding(16.dp)
    ) {
        AlbumItemContent(album, onToggleSave)
    }
}

@Composable
private fun AlbumItemContent(
    album: Album,
    onToggleSave: (Album) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Top
    ) {
        AlbumImage(
            modifier = Modifier.size(128.dp),
            url = album.artworkUrl,
            cardRadius = 10.dp,
            imageRadius = 8.dp,
            imagePadding = 4.dp,
            rotation = -3f,
            elevation = 4.dp
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = album.name,
                    style = Typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                )

                ArrowIcon()
            }

            Text(
                text = album.artistName,
                style = Typography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(top = 4.dp, bottom = 12.dp)
            )

            AlbumChips(
                genres = album.genres.take(3),
                isSaved = album.isSaved,
                onToggleSave = { onToggleSave(album) }
            )
        }
    }
}

@Composable
private fun ArrowIcon() {
    Surface(
        color = ArrowIconBackground,
        shape = CircleShape,
        modifier = Modifier.size(28.dp)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = SectionTabTextUnselected,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AlbumChips(
    genres: List<String>,
    isSaved: Boolean,
    onToggleSave: () -> Unit
) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        genres.forEach { genre ->
            GenreChip(text = genre)
        }

        SaveChip(isSaved = isSaved, onClick = onToggleSave)
    }
}

@Preview(showBackground = true)
@Composable
fun AlbumItemPreview() {
    val sampleAlbum = Album(
        id = "1",
        name = "test test test ",
        artistName = "test test",
        artworkUrl = "",
        releaseDate = "2023",
        appleMusicUrl = "",
        genres = listOf("Latin", "Techno", "Rock"),
        isSaved = false
    )

    MaterialTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            AlbumItem(
                album = sampleAlbum,
                onItemClick = {},
                onToggleSave = {}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AlbumItemSavedPreview() {
    val sampleAlbum = Album(
        id = "2",
        name = "test test test ",
        artistName = "test",
        artworkUrl = "",
        releaseDate = "2023",
        appleMusicUrl = "",
        genres = listOf("Rock", "Techno"),
        isSaved = true
    )

    MaterialTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            AlbumItem(
                album = sampleAlbum,
                onItemClick = {},
                onToggleSave = {}
            )
        }
    }
}
