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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.formakidov.challenge1.data.model.Album
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
            .shadow(elevation = 6.dp, spotColor = Color(0x0F424242), ambientColor = Color(0x0F424242), shape = RoundedCornerShape(20.dp))
            .shadow(elevation = 2.dp, spotColor = Color(0x0F525252), ambientColor = Color(0x0F525252), shape = RoundedCornerShape(20.dp))
            .background(color = Color(0xFFFFFFFF), shape = RoundedCornerShape(size = 20.dp))
            .border(width = 1.dp, color = Color(0xFFE5E5E5), shape = RoundedCornerShape(size = 20.dp))
            .clickable { onItemClick(album) }
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Top
        ) {
            AlbumImage(url = album.artworkUrl)

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
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
                        modifier = Modifier.weight(1f).padding(end = 8.dp)
                    )

                    Surface(
                        color = Color(0xFFF2F2F7),
                        shape = CircleShape,
                        modifier = Modifier.size(28.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                                contentDescription = null,
                                tint = Color(0xFF8E8E93),
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
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
}

@Composable
fun AlbumImage(url: String) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = Color(0xFFF2F2F7),
        modifier = Modifier.size(100.dp)
    ) {
        AsyncImage(
            model = url,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth()
        )
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
        // todo spacebetween with padding
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

@Composable
fun GenreChip(text: String) {
    Chip(
        text = text,
        icon = Icons.Default.MusicNote,
        backgroundColor = Color(0xFFDFF3FC),
        borderColor = Color(0xFFC8E7F0),
        contentColor = Color(0xFF389EC5)
    )
}

@Composable
fun SaveChip(isSaved: Boolean, onClick: () -> Unit) {
    if (isSaved) {
        Chip(
            text = "Saved",
            icon = Icons.Default.Favorite,
            backgroundColor = Color(0xFFDDF7E4),
            borderColor = Color(0xFFD9E0E3),
            contentColor = Color(0xFF31CA57),
            onClick = onClick
        )
    } else {
        Chip(
            text = "Add",
            icon = Icons.Default.FavoriteBorder,
            backgroundColor = Color(0xFFE8EFF1),
            borderColor = Color(0xFFD9E0E3),
            contentColor = Color(0xFF8195AE),
            onClick = onClick
        )
    }
}

@Composable
private fun Chip(
    text: String,
    icon: ImageVector,
    backgroundColor: Color,
    borderColor: Color,
    contentColor: Color,
    onClick: (() -> Unit)? = null
) {
    Surface(
        color = backgroundColor,
        shape = RoundedCornerShape(50),
        border = androidx.compose.foundation.BorderStroke(1.dp, borderColor),
        modifier = Modifier
            .height(28.dp)
            .then(if (onClick != null) Modifier.clickable { onClick() } else Modifier)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(horizontal = 10.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = contentColor,
                modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = text,
                style = Typography.labelSmall.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    color = contentColor
                )
            )
        }
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
